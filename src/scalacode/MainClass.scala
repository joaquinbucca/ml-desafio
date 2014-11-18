package scalacode

import scala.io.Source

/**
 * Created by joaquin on 06/11/14.
 */

object MainClass {
  def main(args: Array[String]) {
    println("System.currentTimeMillis() = " + System.currentTimeMillis())
    for (ln <- Source.stdin.getLines()){
      val line= ln.replaceAll(", ", ",")
      RequestManager.sendRequest(line)
    }
  }
}
