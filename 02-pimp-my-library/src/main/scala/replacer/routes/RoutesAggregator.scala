package replacer.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}

import scala.concurrent.Future


class RoutesAggregator(routes: Route*) extends Route {
  override def apply(requestContext: RequestContext): Future[RouteResult] =
    routes.reduceLeft { (acc, curr) => acc ~ curr }.apply(requestContext)
}

object RoutesAggregator {
  def apply(routes: Route*): RoutesAggregator = new RoutesAggregator(routes: _*)
}
