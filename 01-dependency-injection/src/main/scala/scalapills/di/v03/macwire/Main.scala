package scalapills.di.v03.macwire

import com.softwaremill.macwire._

import scalapills.di.v03.macwire.models.MyData
import scalapills.di.v03.macwire.repositories.{MyRepository, MyRepositoryImpl}
import scalapills.di.v03.macwire.services.{MyService, MyServiceImpl}


object Main extends App {

  lazy val repository: MyRepository = wire[MyRepositoryImpl]
  lazy val service: MyService = wire[MyServiceImpl]

  val data = MyData(1, "Sample Data")

  val resultingData = service.doSomething(data)

  println(resultingData)
}
