package scalapills.di.vXX.sampleapp.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}

import scala.concurrent.Future
import scalapills.di.vXX.sampleapp.models.MyData
import scalapills.di.vXX.sampleapp.services.MyService


class SampleRoute(myService: MyService) extends Route with JsonSupport {
  override def apply(requestContext: RequestContext): Future[RouteResult] =
    path("test") {
      get {
        val data = MyData(1, "Sample Data")
        val resultingData = myService.doSomething(data)
        complete(resultingData)
      }
    }.apply(requestContext)
}

object SampleRoute {
  def apply(myService: MyService): SampleRoute = new SampleRoute(myService)
}