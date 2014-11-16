package scalacode

import java.io.File

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.SmallestMailboxRouter
import akka.util.Timeout

import scala.collection.mutable

object RequestManager {
  implicit val timeout = Timeout(300000)
  val system = ActorSystem("system")
  val file: File = new File("output.log")

  lazy val roundRobinRouter = {
    val requesters = (0 to 15).map { index =>
      system.actorOf(Props(new Processer(index.toString)))
    }
    system.actorOf(Props.empty.withRouter(SmallestMailboxRouter(requesters)))
  }

  var count = 0
  var minute: String = null
  var map = new mutable.HashMap[String, mutable.Map[String, Statistics]]()

  def sendRequest(line: String) = roundRobinRouter ! line
}

class Processer(id: String) extends Actor {

  def resetMap() = {
    RequestManager.map = new mutable.HashMap[String, mutable.Map[String, Statistics]]()
  }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try {
      op(p)
    } finally {
      p.close()
    }
  }

  def saveInDBAndWriteFile() = {
    RequestManager.map.iterator.map { inner =>
      inner._2.iterator.map { statistics =>
        RequestManager.count += 1
        println("id = " + id)
        printToFile(RequestManager.file) { ln => statistics._2.getLine.foreach(ln.println)}
        //todo : hacer que esta linea se guarde
      }
    }
  }

  def saveInMap(data: EntryData) = {
    var secondMap: mutable.Map[String, Statistics] = RequestManager.map.getOrElse(data.getIpFrom, new mutable.HashMap[String, Statistics]())

    val statistics: Statistics = secondMap.getOrElse(data.getIpTo, new Statistics(data))
    statistics.update(data)
    secondMap += data.getIpTo -> statistics
    RequestManager.map += data.getIpFrom -> secondMap
    RequestManager.count = RequestManager.count + 1
//    println("RequestManager.count = " + RequestManager.count)
    if(RequestManager.count > 54900){
      println("System.currentTimeMillis() = " + System.currentTimeMillis())
    }
  }

  override def receive: Receive = {
    case line: String =>

      val attrs: List[Option[String]] = line.split("\\s+").toList.map(Option(_))
      def checkIfDash(str: Option[String]) = if (str.getOrElse("-").equals("-")) Some(str) else None
      attrs.map { attr => checkIfDash(attr)}

      val minute = attrs(0).getOrElse("")
      def changeMinute(min: String) = RequestManager.minute = min

      if (RequestManager.minute == null) changeMinute(minute)
      if (!RequestManager.minute.equals(minute)) {
        changeMinute(minute)
        resetMap()
        saveInDBAndWriteFile()
      }
      saveInMap(new EntryData(attrs))

    //      RequestManager.count = RequestManager.count + 1
    //      println(s"Requester: $id Request = " + RequestManager.count)
    //      sender ! Source.fromInputStream(connection.getInputStream).mkString("")
  }
}

class EntryData(attributes: List[Option[String]]) {
  val minute: Option[String] = attributes(0)
  val upstream: Option[String] = attributes(2)
  val status: Int = attributes(3).getOrElse("0").toInt
  val request_time: Float = attributes(4).getOrElse("0").toFloat
  val upstrea_address: Option[String] = attributes(7)
  val client_ip: Option[String] = attributes(8)
  val request_length: Long = attributes(9).getOrElse("0").toLong
  val bytes_sent: Long = attributes(10).getOrElse("0").toLong
  val nginx_host: Option[String] = attributes(15)

  def getIpTo: String = upstream.getOrElse(upstrea_address.getOrElse("No tenia"))

  def getIpFrom: String = client_ip.getOrElse("No tenia")

  def getMinute: String = minute.getOrElse("")

  def getNginx: String = nginx_host.getOrElse("")
}

class Statistics(data: EntryData) {
  var totalOcurrencies: Int = 0
  var totalRequestTime: Float = 0f
  var totalRequestBytes: Long = 0L
  var totalBytesSent: Long = 0L
  var req10: Int = 0
  var req50: Int = 0
  var req100: Int = 0
  var req300: Int = 0
  var req1000: Int = 0
  var req10000: Int = 0

  def update(data: EntryData) = {
    if (true) totalOcurrencies += 1
    totalRequestTime += data.request_time
    totalRequestBytes += data.request_length
    totalBytesSent += data.bytes_sent

    if (data.request_time < 0.01) req10 += 1
    else if (data.request_time < 0.050) req50 += 1
    else if (data.request_time < 0.100) req100 += 1
    else if (data.request_time < 0.300) req300 += 1
    else if (data.request_time < 1.000) req1000 += 1
    else if (data.request_time < 10.000) req10000 += 1

  }

  def getLine: String = {
    data.getMinute + "\t" + data.getNginx + "\t" + data.getIpFrom + "\t" + data.getIpTo + "\t" +
      data.status + "\t" + totalOcurrencies + "\t" + totalRequestTime + "\t" + totalRequestBytes + "\t" +
      totalBytesSent + "\t" + req10 + "\t" + req50 + "\t" + req100 + "\t" + req300 + "\t" + req1000 +
      "\t" + req10000
  }
}

