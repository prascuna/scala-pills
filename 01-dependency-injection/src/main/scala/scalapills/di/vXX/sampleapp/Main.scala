package scalapills.di.vXX.sampleapp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scalapills.di.vXX.sampleapp.repositories.MyRepositoryImpl
import scalapills.di.vXX.sampleapp.routes.{RoutesAggregator, SampleRoute}
import scalapills.di.vXX.sampleapp.services.MyServiceImpl


object Main extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  lazy val repository = new MyRepositoryImpl
  lazy val service = new MyServiceImpl(repository)


  val routes = RoutesAggregator(
    SampleRoute(service)
   // SampleRoute(service),
  )

  Http().bindAndHandle(routes, "localhost", 9999)
}
