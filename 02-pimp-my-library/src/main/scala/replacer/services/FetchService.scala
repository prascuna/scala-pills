package replacer.services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, Uri}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

import scala.concurrent.{ExecutionContext, Future}

trait FetchService {
  def fetch(httpMethod: HttpMethod, uri: Uri): Future[String]
}

class FetchServiceImpl(implicit ec: ExecutionContext, actorSystem: ActorSystem, actorMaterializer: ActorMaterializer) extends FetchService {

  override def fetch(httpMethod: HttpMethod, uri: Uri): Future[String] =
    Http()
      .singleRequest(HttpRequest(httpMethod, uri))
      .flatMap(_.entity.dataBytes.runWith(Sink.fold("")((acc, curr) => acc + curr.decodeString("UTF-8"))))
}