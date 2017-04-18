package scalapills.di.v05.thincake

import scalapills.di.v05.thincake.models.MyData
import scalapills.di.v05.thincake.repositories.{MyRepositoryModule, MyRepositoryModuleImpl}
import scalapills.di.v05.thincake.services.{MyServiceModule, MyServiceModuleImpl}


object Main extends App with MyServiceModuleImpl with MyRepositoryModuleImpl{

  val data = MyData(1, "Sample Data")

  val resultingData = myService.doSomething(data)

  println(resultingData)
}
