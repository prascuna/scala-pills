package replacer.services

import replacer.models.MyData
import replacer.repositories.MyRepository

import scala.util.Random

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
