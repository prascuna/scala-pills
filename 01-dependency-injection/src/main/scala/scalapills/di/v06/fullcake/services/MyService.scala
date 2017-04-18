package scalapills.di.v06.fullcake.services

import scala.util.Random
import scalapills.di.v06.fullcake.models.MyData
import scalapills.di.v06.fullcake.repositories.MyRepositoryModule

trait MyServiceModule {
  trait MyService {
    def doSomething(data: MyData): Option[MyData]
  }

  def myService: MyService
}

trait MyServiceModuleImpl extends MyServiceModule{
  self: MyRepositoryModule =>

  class MyServiceImpl(repository: MyRepository) extends MyService {

    override def doSomething(data: MyData): Option[MyData] = {
      println("Doing something with the data")
      val newData = data.copy(name = data.name + " " + Random.nextInt())
      repository.write(newData)
      repository.read(newData.id)
    }

  }

  lazy val myService = new MyServiceImpl(myRepository)
}


