package scalapills.dockerit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scalapills.dockerit.config.AppConfig
import scalapills.dockerit.repositories.{DatabaseAccess, FlywayRunner, MyRepository}
import scalapills.dockerit.routes.{RoutesAggregator, SampleRoute}
import scalapills.dockerit.services.{LocationService, MyService}

object Main extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val appConfig = new AppConfig(ConfigFactory.load())


  val dbAccess = DatabaseAccess(appConfig.sampleDbConfig) {
    FlywayRunner(_).migrate
  }


  lazy val repository = MyRepository(dbAccess)

  lazy val service = MyService(repository)
  lazy val locationService = LocationService(appConfig.serverConfig)


  val routes = RoutesAggregator(
    SampleRoute(service, locationService)
  )

  Http().bindAndHandle(routes, appConfig.serverConfig.host, appConfig.serverConfig.port)
    .foreach(serverBindings => println(s"Server Started at ${serverBindings.localAddress}"))
}
