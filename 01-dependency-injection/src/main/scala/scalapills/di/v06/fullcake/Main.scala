package scalapills.di.v06.fullcake

import scalapills.di.v06.fullcake.models.MyData
import scalapills.di.v06.fullcake.repositories.MyRepositoryModuleImpl
import scalapills.di.v06.fullcake.services.MyServiceModuleImpl


object Main extends App with MyServiceModuleImpl with MyRepositoryModuleImpl {

  val data = MyData(1, "Sample Data")

  val resultingData = myService.doSomething(data)


  println(resultingData)

  // What's the type of myService?
  // val ms = myService
}
