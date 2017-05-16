import sbt.Keys.crossScalaVersions

name := "Scala Pills"

val akkaHttpVersion = "10.0.6"
lazy val commonSettings = Seq(
  organization := "com.github",
  scalaVersion := "2.12.1",
  version := "0.1.0-SNAPSHOT",
  crossScalaVersions := Seq("2.12.1", "2.11.8"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "org.mockito" % "mockito-core" % "2.7.22" % Test,
    "org.scalatest" %% "scalatest" % "3.0.3" % Test
  )
)

lazy val dependencyInjection = (project in file("01-dependency-injection"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"

  ))

lazy val pimpMyLibrary = (project in file("02-pimp-my-library"))
  .settings(commonSettings: _*)

lazy val integrationTests = (project in file("03-docker-integration-test"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= {
    val dockerTestKitVersion = "0.9.3"
    val slickVersion = "3.2.0"
    Seq(
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "mysql" % "mysql-connector-java" % "6.0.6",
      "org.flywaydb" % "flyway-core" % "4.2.0",
      "ch.qos.logback" % "logback-classic" % "1.2.2",
      "com.whisk" %% "docker-testkit-scalatest" % dockerTestKitVersion % Test,
      "com.whisk" %% "docker-testkit-impl-docker-java" % dockerTestKitVersion % Test
    )
  })

lazy val root = (project in file("."))
  .aggregate(dependencyInjection)
  .settings(commonSettings: _*)
