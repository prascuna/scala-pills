package scalapills.di.v07.functions

import scalapills.di.v07.functions.models.MyData
import scalapills.di.v07.functions.repositories.MyRepository
import scalapills.di.v07.functions.services.MyService


object Main extends App {

  val data = MyData(1, "Sample Data")

  val resultingData = MyService.doSomething(data)(MyRepository.read , MyRepository.write)

  println(resultingData)
}
