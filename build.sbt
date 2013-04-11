name := "webdir"

version := "0.1.0"

organization := "com.github.philcali"

scalaVersion := "2.9.2"

libraryDependencies <++= (sbtVersion) { sbtv =>
  Seq(
    "org.scala-sbt" % "launcher-interface" % sbtv % "provided",
    "com.github.philcali" %% "lmxml-html" % "0.1.3",
    "net.databinder" %% "unfiltered-netty-uploads" % "0.6.8"
  )
}
