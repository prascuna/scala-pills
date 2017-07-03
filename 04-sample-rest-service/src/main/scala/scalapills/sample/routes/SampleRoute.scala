package scalapills.sample.routes

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{Conflict, Created, InternalServerError, NotFound}
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scalapills.sample.exceptions.DuplicatedEntryException
import scalapills.sample.models.{MyData, Test, Whatever}
import scalapills.sample.services.{LocationService, MyService}


class SampleRoute(myService: MyService, locationService: LocationService) extends Route with JsonSupport {


  override def apply(requestContext: RequestContext): Future[RouteResult] = {
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
                case Success(Some(data)) =>
                  complete(data)
                case Success(None) => complete(NotFound)
                case Failure(e) =>
                  e.printStackTrace()
                  complete(InternalServerError)
              }
            }
          }
        }
    } ~
      pathPrefix("test") {
        pathEnd {
          get {
            complete(Test("asfd", List(
              Whatever(1, "blah"),
              Whatever(2, "blah2")
            )))
          }
        }
      }
  }.apply(requestContext)
}

object SampleRoute {
  def apply(myService: MyService, locationService: LocationService): SampleRoute = new SampleRoute(myService, locationService)
}