package com.github.philcali.web

import unfiltered.netty._
import unfiltered.request._
import unfiltered.response._

import unfiltered.netty.request._

import java.io.File

case class WebApp(cwd: File) extends async.MultiPartDecoder with FileSystem with ServerErrorResponse {

  def intent = {
    case POST(Path(FileCheck(dir)) & MultiPart(req)) => {
      case Decode(binding) => {
        val disk = MultiPartParams.Disk(binding)
        disk.files("file") match {
          case Seq(file, _*) =>
            file.write(new File(dir, file.name))
            req.respond(Redirect(req.uri))
        }
      }
    }
  }

  def pass = MultiPartPass.DefaultPassHandler
}
