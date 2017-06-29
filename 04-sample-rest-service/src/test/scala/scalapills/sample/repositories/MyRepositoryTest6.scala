package scalapills.sample.repositories

import com.typesafe.config.ConfigFactory
import com.whisk.docker.scalatest.DockerTestKit
import org.scalatest.{AsyncWordSpec, Matchers}

import scalapills.sample.config.AppConfig
import scalapills.sample.exceptions.DuplicatedEntryException
import scalapills.sample.models.MyData
import scalapills.sample.testkit.DockerMySQLService

class MyRepositoryTest6 extends AsyncWordSpec with Matchers with DockerTestKit with DockerMySQLService {
  override def MySQLPort: Int = 60006

  lazy val dbConfig = new AppConfig(ConfigFactory.load()).sampleDbConfig.copy(
    uri = s"jdbc:mysql://localhost:$MySQLPort/sampledb?autoReconnect=true&useSSL=false"
  )
  lazy val dbAccess = DatabaseAccess(dbConfig) {
    FlywayRunner(_).migrate
  }

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
  lazy val myRepository = MyRepository(dbAccess)

  "MyRepository" when {
    "writing a non existing item" should {
      "write it in the db and return it back" in {
        val dataId = 1
        val myData = MyData(Some(dataId), "value 1")
        for {
          writeResult <- myRepository.write(myData)
          readResult <- myRepository.read(dataId)
        } yield {
          writeResult shouldBe Right(myData)
          readResult shouldBe Some(myData)
        }
      }
    }

    "writing an already existing item" should {
      "not write it and return a Failure" in {
        val dataId = 2
        val myData = MyData(Some(dataId), "value 2")
        for {
          _ <- myRepository.write(myData)
          writeResult2 <- myRepository.write(myData)
        } yield {
          writeResult2 shouldBe Left(DuplicatedEntryException)
        }
      }

    }
  }
}
