package scalapills.di.v06.fullcake.repositories

import scala.collection.mutable
import scalapills.di.v06.fullcake.models.MyData



trait MyRepositoryModule {
  // The definition of the components is moved inside the Module
  trait MyRepository {
    def read(id: Long): Option[MyData]

    def write(data: MyData): Unit
  }

  def myRepository: MyRepository
}

trait MyRepositoryModuleImpl extends MyRepositoryModule {

  class MyRepositoryImpl extends MyRepository {
    val fakeDb = mutable.Map[Long, MyData]()

    override def read(id: Long): Option[MyData] =
      fakeDb.get(id)

    override def write(data: MyData): Unit =
      fakeDb.put(data.id, data)
  }

  lazy val myRepository: MyRepository = new MyRepositoryImpl
}