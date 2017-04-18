package scalapills.di.v01.guice.services

import javax.inject.Inject

import scala.util.Random
import scalapills.di.v01.guice.models.MyData
import scalapills.di.v01.guice.repositories.MyRepository

trait MyService {

  def doSomething(data: MyData): Option[MyData]

}

class MyServiceImpl @Inject()(repository: MyRepository) extends MyService {

  override def doSomething(data: MyData): Option[MyData] = {
    println("Doing something with the data")
    val newData = data.copy(name = data.name + " " + Random.nextInt())
    repository.write(newData)
    repository.read(newData.id)
  }

}
