package scalapills.sample.routes

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{Conflict, Created, InternalServerError, NotFound}
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scalapills.sample.exceptions.DuplicatedEntryException
import scalapills.sample.models.MyData
import scalapills.sample.services.{LocationService, MyService}


class SampleRoute(myService: MyService, locationService: LocationService) extends Route with JsonSupport {
  override def apply(requestContext: RequestContext): Future[RouteResult] =
    pathPrefix("data") {
      post {
        entity(as[MyData]) { data =>
          pathEnd {
            onComplete(myService.write(data)) {
              case Success(Right(storedData)) =>
                val locationHeader = Location(locationService.locate(storedData))
                complete(HttpResponse(Created, headers = List(locationHeader)))
              case Success(Left(DuplicatedEntryException)) =>
                val locationHeader = Location(locationService.locate(data))
                complete(HttpResponse(Conflict, headers = List(locationHeader)))
              case Success(Left(e)) =>
                e.printStackTrace()
                complete(InternalServerError, e.getMessage)
              case Failure(e) =>
                e.printStackTrace()
                complete(InternalServerError, e.getMessage)
            }
          }
        }
      } ~
        path(IntNumber) { id =>
          pathEnd {
            get {
              onComplete(myService.read(id)) {
                case Success(Some(data)) => complete(data)
                case Success(None) => complete(NotFound)
                case Failure(e) =>
                  e.printStackTrace()
                  complete(InternalServerError)
              }
            }
          }
        }
    }.apply(requestContext)
}

object SampleRoute {
  def apply(myService: MyService, locationService: LocationService): SampleRoute = new SampleRoute(myService, locationService)
}