import java.io.{InputStreamReader, BufferedReader}
import java.net.{URL, InetSocketAddress, Proxy}

import scala.collection.mutable
import scala.collection.mutable.Queue
import scala.concurrent.{Promise, Future}
import akka.actor.{ActorRef, Actor, Props}
import akka.actor.ActorSystem
import akka.routing.{SmallestMailboxRouter, RoundRobinRouter}
import akka.util.Timeout
import akka.pattern.ask

import scala.io.Source

object RequestManager  {
  implicit val timeout = Timeout(300000)
  val system= ActorSystem("system")

  lazy val roundRobinRouter = {
    val requesters = (0 to 15).map { index =>
      system.actorOf(Props(new Processer(index.toString)))
    }
    system.actorOf(Props.empty.withRouter(SmallestMailboxRouter(requesters)))
  }
  var count = 0
  var minute:String= null
  var map= new mutable.HashMap[String, mutable.Map[String, Statistics]]()

  def sendRequest(line: String): Future[String] = (roundRobinRouter ? LogLine(line)) map {
    case response: String => response
  }
}

class Processer(id: String) extends Actor{
  val proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 9050))

  def resetMap() = {
    RequestManager.map= new mutable.HashMap[String, mutable.Map[String, Statistics]]()
  }

  def saveInDBAndWriteFile() = {
    RequestManager.map.iterator.map{ inner =>
      inner._2.iterator.map{ statistics =>
        statistics._2.getLine
        //todo : hacer que esta linea se guarde
      }
    }
  }

  def saveInMap(data: EntryData) = ???

  override def receive: Receive = {
    case LogLine(line) =>

      val attrs: List[String] = line.split(" ").toList
      def checkIfDash(str: String) = if(str.equals("-")) Some(str) else None
      attrs.map{ attr => checkIfDash(attr) }

      val minute= attrs(0)
      def changeMinute(min: String) = RequestManager.minute= min

      if(RequestManager.minute == null) changeMinute(minute)
      if(!(RequestManager.minute).equals(minute)) {
        changeMinute(minute)
        resetMap()
        saveInDBAndWriteFile()
      }
      saveInMap(new EntryData(attrs))

      RequestManager.count = RequestManager.count + 1
      println(s"Requester: $id Request = " + RequestManager.count)
      sender ! Source.fromInputStream(connection.getInputStream).mkString("")
  }
}

case class LogLine(line: String)

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

  def getIpTo :String = upstream.getOrElse(upstrea_address.getOrElse("No tenia"))
  def getIpFrom : String = client_ip.getOrElse("No tenia")
  def getMinute : String = minute.getOrElse("")
  def getNginx : String = nginx_host.getOrElse("")
}

