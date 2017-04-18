package scalapills.di.v02.constructor.services

import scala.util.Random
import scalapills.di.v02.constructor.models.MyData
import scalapills.di.v02.constructor.repositories.MyRepository

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
