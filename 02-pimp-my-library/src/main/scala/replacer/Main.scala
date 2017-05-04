package replacer

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import replacer.config.AppConfig
import replacer.repositories.MyRepositoryImpl
import replacer.routes.{ReplacerRoute, RoutesAggregator, SampleRoute}
import replacer.services.{FetchServiceImpl, MyServiceImpl, ReplacerServiceImpl, StringReplacerImpl}


object Main extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val ec = concurrent.ExecutionContext.Implicits.global

  val config = new AppConfig(ConfigFactory.load())

  lazy val repository = new MyRepositoryImpl
  lazy val service = new MyServiceImpl(repository)

  lazy val fetchService = new FetchServiceImpl()
  lazy val stringReplacer = new StringReplacerImpl
  lazy val replacerService = new ReplacerServiceImpl(config.replacerConfig, fetchService, stringReplacer)


  val routes = RoutesAggregator(
    SampleRoute(service),
    ReplacerRoute(replacerService)
  )

  val serverConfig = config.serverConfig

  Http().bindAndHandle(routes, serverConfig.host, serverConfig.port)
}
