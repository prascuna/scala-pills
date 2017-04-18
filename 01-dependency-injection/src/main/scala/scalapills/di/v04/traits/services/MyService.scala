package scalapills.di.v04.traits.services

import scala.util.Random
import scalapills.di.v04.traits.models.MyData
import scalapills.di.v04.traits.repositories.MyRepository

trait MyService {
  def doSomething(data: MyData): Option[MyData]
}

trait MyServiceImpl extends MyService {
  self: MyRepository =>
  override def doSomething(data: MyData): Option[MyData] = {
    println("Doing something with the data")
    val newData = data.copy(name = data.name + " " + Random.nextInt())
    self.write(newData)
    self.read(newData.id)
  }

}