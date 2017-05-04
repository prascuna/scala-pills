package replacer.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import replacer.models.MyData
import replacer.services.MyService

import scala.concurrent.Future


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