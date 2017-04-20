import sbt.Keys.crossScalaVersions

name := "Scala Pills"

lazy val commonSettings = Seq(
  organization := "com.github",
  scalaVersion := "2.12.1",
  version := "0.1.0-SNAPSHOT",
  crossScalaVersions := Seq("2.12.1", "2.11.8"),
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val dependencyInjection = (project in file("01-dependency-injection"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided",
    "com.typesafe.akka" %% "akka-http" % "10.0.4",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.4",
    "org.mockito" % "mockito-core" % "2.7.22"
  ))

lazy val root = (project in file("."))
  .aggregate(dependencyInjection)
  .settings(commonSettings: _*)
