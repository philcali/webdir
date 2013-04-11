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

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
  <url>https://github.com/philcali/webdir</url>
  <licenses>
    <license>
      <name>The MIT License</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:philcali/webdir.git</url>
    <connection>scm:git:git@github.com:philcali/webdir.git</connection>
  </scm>
  <developers>
    <developer>
      <id>philcali</id>
      <name>Philip Cali</name>
      <url>http://philcalicode.blogspot.com/</url>
    </developer>
  </developers>
)
