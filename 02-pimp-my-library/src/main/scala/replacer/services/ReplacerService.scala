package replacer.services

import akka.http.scaladsl.model.{HttpMethod, HttpMethods, Uri}
import com.typesafe.config.Config

import scala.concurrent.{ExecutionContext, Future}

trait ReplacerService {
  def replace(oldString: String, newString: String): Future[String]
}


class ReplacerServiceImpl(config: Config, fetchService: FetchService, stringReplacer: StringReplacer)(implicit ec: ExecutionContext) extends ReplacerService {
  override def replace(oldString: String, newString: String): Future[String] = {

    val uri = Uri(config.getString("replacer.uri"))

    val method = getHttpMethod("replacer.method")

    fetchService.fetch(method, uri).map(stringReplacer.replace(_, oldString, newString))
  }

  private def getHttpMethod(path: String): HttpMethod =
    config.getString(path) match {
      case "CONNECT" => HttpMethods.CONNECT
      case "DELETE" => HttpMethods.DELETE
      case "GET" => HttpMethods.GET
      case "HEAD" => HttpMethods.HEAD
      case "OPTIONS" => HttpMethods.OPTIONS
      case "PATCH" => HttpMethods.PATCH
      case "POST" => HttpMethods.POST
      case "PUT" => HttpMethods.PUT
      case "TRACE" => HttpMethods.TRACE
    }
}
