package scalapills.di.v01.guice

import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

import scalapills.di.v01.guice.models.MyData
import scalapills.di.v01.guice.modules.MyModule
import scalapills.di.v01.guice.services.MyService


object Main extends App {

  val injector = Guice.createInjector(new MyModule())

  val service = injector.instance[MyService]


  val data = MyData(1, "Sample Data")

  val resultingData = service.doSomething(data)

  println(resultingData)

}
