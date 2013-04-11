package com.github.philcali.web

import unfiltered.netty._
import unfiltered.request._
import unfiltered.response._

import unfiltered.netty.request._

import java.io.File
import java.io.InputStream
import java.io.OutputStream

import lmxml.{ XmlFormat, XmlConvert }
import lmxml.transforms.{ Transform, Value, If, Foreach }

case class FileTree(cwd: File) extends async.Plan with FileSystem with ServerErrorResponse {

  object Image extends Params.Extract(
    "image",
    Params.first ~> Params.nonempty
  )

  object Delete extends Params.Extract(
    "delete",
    Params.first ~> Params.nonempty
  )

  def loadImage(image: String) = {
    val byteStream = new java.io.ByteArrayOutputStream()
    val input = getClass.getResourceAsStream("/" + image)
    pump(input, byteStream)
    byteStream.toByteArray
  }

  def pump(in: InputStream, out: OutputStream) {
    val bytes = new Array[Byte](1024)
    in.read(bytes) match {
      case n if n > 0 => out.write(bytes, 0, n); pump(in, out);
      case _ => out.close(); in.close();
    }
  }

  val xmlResponse = XmlConvert andThen XmlFormat(300, 2)

  def intent = {
    case req @ GET(Path("/") & Params(Image(image))) =>
      req.respond(
        ContentType("image/png") ~>
        ResponseBytes(loadImage(image))
      )

    case req @ POST(Params(Delete(file))) =>
      new File(cwd, file).delete()
      req.respond(Redirect(req.uri))

    case req @ GET(Path(FileCheck(dir))) =>
      val stripSlash = (directory: String) =>
        if (directory == "./") ""
        else directory.replace("./", "/")

      val transform = Transform(
        "directory" -> Value(stripSlash(dir.toString)),
        "path" -> Value(dir.getAbsolutePath.replace("./", "")),
        "files" -> Foreach(dir.listFiles) { file => Seq(
          "filename" -> Value(file.getName),
          "if-is-directory" -> If(file.isDirectory)()
        ) }
      )
      val resp = Lmxml.fromResource("directory.lmxml")(transform andThen xmlResponse)

      req.respond(ResponseString(resp))
   }
}

