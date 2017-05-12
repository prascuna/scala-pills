package replacer.config

import java.net.URL

import akka.http.scaladsl.model.{HttpMethod, HttpMethods, Uri}
import com.typesafe.config.Config

class AppConfig(config: Config) {

  import AppConfig._

  val serverConfig = ServerConfig(
    host = config.getString("server.host"),
    port = config.getInt("server.port")
  )

  val replacerConfig = ReplacerConfig(
    uri = config.getURI("replacer.uri"),
    method = config.getHttpMethod("replacer.method")
  )

}

object AppConfig {

  implicit class RichConfig(config: Config) {
    def getURI(path: String): Uri = Uri(new URL(config.getString(path)).toString)

    def getHttpMethod(path: String): HttpMethod =
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

}

case class ServerConfig(host: String, port: Int)

case class ReplacerConfig(uri: Uri, method: HttpMethod)
