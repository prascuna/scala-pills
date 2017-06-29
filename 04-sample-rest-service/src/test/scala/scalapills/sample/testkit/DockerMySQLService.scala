package scalapills.sample.testkit

import java.io.File

import com.typesafe.scalalogging.LazyLogging
import com.whisk.docker.DockerReadyChecker.LogLineContains
import com.whisk.docker.impl.dockerjava.DockerKitDockerJava
import com.whisk.docker.{DockerContainer, DockerKit, VolumeMapping}

trait DockerMySQLService extends DockerKit with DockerKitDockerJava with LazyLogging {
  private val configDir = new File(".").getCanonicalPath + "/docker/mysql"

  def MySQLPort: Int

  val mysqlContainer = DockerContainer("mysql:5.6.35")
    .withEnv(
      "MYSQL_ALLOW_EMPTY_PASSWORD=yes")
    .withPorts((3306, Some(MySQLPort)))
    .withVolumes(Seq(
      VolumeMapping(s"$configDir/initdb", "/docker-entrypoint-initdb.d"),
      VolumeMapping(s"$configDir/conf.d", "/etc/mysql/conf.d")
    ))
    .withReadyChecker(LogLineContains("socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)"))


  abstract override def dockerContainers: List[DockerContainer] = mysqlContainer :: super.dockerContainers

  override def startAllOrFail(): Unit = {
    logger.info(s"Starting MySQL Docker container at port $MySQLPort")
    super.startAllOrFail()
  }

  override def stopAllQuietly(): Unit = {
    logger.info(s"Stopping MySQL Docker container at port $MySQLPort")
    super.stopAllQuietly()
  }
}
