package scalapills.dockerit.config

import java.util.Properties

import akka.http.scaladsl.model.Uri
import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.JdbcProfile

class AppConfig(config: Config) {

  import AppConfig._

  val serverConfig = ServerConfig(
    scheme = config.getString("app.scheme"),
    host = config.getString("app.host"),
    port = config.getInt("app.port")
  )

  val sampleDbConfig = DbConfig(
    uri = config.getUri("sampledb.url"),
    user = config.getString("sampledb.user"),
    password = config.getString("sampledb.password"),
    profile = config.getJdbcProfile("sampledb.jdbcProfile"),
    maxConnections = config.getInt("sampledb.maxConnections")
  )
}


case class ServerConfig(scheme: String, host: String, port: Int)

case class DbConfig(uri: Uri, user: String, password: String, profile: JdbcProfile, maxConnections: Int)

object AppConfig {

  implicit class RichConfig(config: Config) {
    def getUri(path: String): Uri =
      Uri(config.getString(path))

    def getJdbcProfile(path: String): JdbcProfile =
      config.getString(path) match {
        case "slick.jdbc.MySQLProfile" => slick.jdbc.MySQLProfile
      }
  }

  def asTypeSafeConfig[C: SerializableConfig](config: C) = {
    implicitly[SerializableConfig[C]].serialize(config)
  }

}

trait SerializableConfig[T] {
  def serialize(config: T): Config
}

object SerializableConfig {
  implicit val dbConfigSerializer = new SerializableConfig[DbConfig] {
    override def serialize(config: DbConfig): Config = {
      import scala.collection.JavaConverters._
      val p = new Properties()
      p.putAll(
        Map[String, String](
          "url" -> config.uri.toString(),
          "user" -> config.user,
          "password" -> config.password,
          "jdbcProfile" -> config.profile.toString(),
          "maxConnections" -> config.maxConnections.toString
        ).asJava
      )
      ConfigFactory.parseProperties(p)
    }
  }
}