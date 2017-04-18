package scalapills.di.v05.thincake.services

import scala.util.Random
import scalapills.di.v05.thincake.models.MyData
import scalapills.di.v05.thincake.repositories.{MyRepository, MyRepositoryModule}

trait MyServiceModule {
  def myService: MyService
}

trait MyServiceModuleImpl extends MyServiceModule {
  self: MyRepositoryModule =>

  lazy val myService = new MyServiceImpl(myRepository)
}


trait MyService {
  def doSomething(data: MyData): Option[MyData]
}

class MyServiceImpl(repository: MyRepository) extends MyService {

  override def doSomething(data: MyData): Option[MyData] = {
    println("Doing something with the data")
    val newData = data.copy(name = data.name + " " + Random.nextInt())
    repository.write(newData)
    repository.read(newData.id)
  }

}