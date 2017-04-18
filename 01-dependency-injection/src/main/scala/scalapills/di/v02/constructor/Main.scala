package scalapills.di.v02.constructor


import scalapills.di.v02.constructor.models.MyData
import scalapills.di.v02.constructor.repositories.{MyRepository, MyRepositoryImpl}
import scalapills.di.v02.constructor.services.{MyService, MyServiceImpl}

object Main extends App {

  lazy val repository: MyRepository = new MyRepositoryImpl
  lazy val service: MyService = new MyServiceImpl(repository)

  val data = MyData(1, "Sample Data")

  val resultingData = service.doSomething(data)

  println(resultingData)
}
