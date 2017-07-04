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
    "org.mockito" % "mockito-core" % "2.7.22" % Test,
    "org.scalatest" %% "scalatest" % "3.0.3" % Test
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

lazy val dependencyInjection = (project in file("01-dependency-injection"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"

  ))

lazy val pimpMyLibrary = (project in file("02-pimp-my-library"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  ))

lazy val integrationTests = (project in file("03-docker-integration-test"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= {
    val dockerTestKitVersion = "0.9.3"
    val slickVersion = "3.2.0"
    Seq(
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "mysql" % "mysql-connector-java" % "6.0.6",
      "org.flywaydb" % "flyway-core" % "4.2.0",
      "ch.qos.logback" % "logback-classic" % "1.2.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      "com.whisk" %% "docker-testkit-scalatest" % dockerTestKitVersion % Test,
      "com.whisk" %% "docker-testkit-impl-docker-java" % dockerTestKitVersion % Test
    )
  })

lazy val sampleRestService = (project in file("04-sample-rest-service"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= {
    val dockerTestKitVersion = "0.9.3"
    val slickVersion = "3.2.0"
    val circeVersion = "0.8.0"
    Seq(
      "de.heikoseeberger" %% "akka-http-circe" % "1.17.0",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-generic-extras" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "mysql" % "mysql-connector-java" % "6.0.6",
      "org.flywaydb" % "flyway-core" % "4.2.0",
      "ch.qos.logback" % "logback-classic" % "1.2.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      "com.whisk" %% "docker-testkit-scalatest" % dockerTestKitVersion % Test,
      "com.whisk" %% "docker-testkit-impl-docker-java" % dockerTestKitVersion % Test
    )
  })


lazy val root = (project in file("."))
  .aggregate(dependencyInjection, pimpMyLibrary, integrationTests, sampleRestService)
  .settings(commonSettings: _*)
