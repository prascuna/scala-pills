package replacer.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import replacer.services.ReplacerService

import scala.concurrent.Future
import scala.util.{Failure, Success}

class ReplacerRoute(replacerService: ReplacerService) extends Route with JsonSupport {
  override def apply(requestContext: RequestContext): Future[RouteResult] =
    path("replace") {
      get {
        parameters('oldString ? "", 'newString ? "") { (oldString, newString) =>
          onComplete(replacerService.replace(oldString, newString)) {
            case Success(html) =>
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, html))
            case Failure(e) =>
              println(e)
              complete(StatusCodes.InternalServerError)
          }
        }
      }
    }.apply(requestContext)
}

object ReplacerRoute {
  def apply(replacerService: ReplacerService): ReplacerRoute = new ReplacerRoute(replacerService)
}