package replacer.config

import akka.http.scaladsl.model.{HttpMethod, HttpMethods, Uri}
import com.typesafe.config.Config

class AppConfig(config: Config) {

  val serverConfig = ServerConfig(
    host = config.getString("server.host"),
    port = config.getInt("server.port")
  )

  val replacerConfig = ReplacerConfig(
    uri = Uri(config.getString("replacer.uri")),
    method = config.getString("replacer.method") match {
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
  )

}

case class ServerConfig(host: String, port: Int)

case class ReplacerConfig(uri: Uri, method: HttpMethod)
