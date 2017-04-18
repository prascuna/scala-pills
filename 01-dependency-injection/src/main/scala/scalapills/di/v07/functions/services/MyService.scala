package scalapills.di.v07.functions.services

import scala.util.Random
import scalapills.di.v07.functions.models.MyData

object MyService {
  def doSomething(data: MyData)(
    read: (Long) => Option[MyData],
    write: (MyData) => Unit
  ): Option[MyData] = {
    println("Doing something with the data")
    val newData = data.copy(name = data.name + " " + Random.nextInt())
    write(newData)
    read(newData.id)
  }
}