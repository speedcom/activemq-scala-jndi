lazy val root = (project in file(".")).settings(
 name         := "activemq-scala",
 version      := "1.0.0",
 scalaVersion := "2.11.4",
 libraryDependencies ++= Seq(
  "org.apache.activemq" % "activemq-all" % "5.11.0",
  "com.jsuereth" %% "scala-arm" % "1.4"
  )
)

