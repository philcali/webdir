package com.github.philcali.web

import unfiltered.netty.{ Http, Resources }
import unfiltered.util.Port

import java.net.URL
import java.io.File

object App {
  val RegPort = """-p\s+(\d+)""".r

  def main(args: Array[String]) {
    val cwd = new File(".")
    val resources = Resources(cwd.toURI.toURL)
    var port = args.mkString(" ") match {
      case RegPort(port) => port.toInt
      case _ => Port.any
    }

    val http =
      Http(port, "0.0.0.0")
        .plan(resources)
        .plan(FileTree(cwd))
        .plan(WebApp(cwd))

    http.run
    println(s"Server started on ${port}. Press enter to kill")
    Console.readLine
    http.stop
  }
}
