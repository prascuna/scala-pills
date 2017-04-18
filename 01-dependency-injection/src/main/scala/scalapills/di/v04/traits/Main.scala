package scalapills.di.v04.traits



import scalapills.di.v04.traits.models.MyData
import scalapills.di.v04.traits.repositories.MyRepositoryImpl
import scalapills.di.v04.traits.services.MyServiceImpl


object Main extends App with MyServiceImpl with MyRepositoryImpl {

  val data = MyData(1, "Sample Data")

  val resultingData = this.doSomething(data)

  println(resultingData)
}
