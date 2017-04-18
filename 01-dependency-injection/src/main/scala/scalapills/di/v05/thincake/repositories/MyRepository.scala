package scalapills.di.v05.thincake.repositories

import scala.collection.mutable
import scalapills.di.v05.thincake.models.MyData


trait MyRepositoryModule {
  def myRepository: MyRepository
}

trait MyRepositoryModuleImpl extends MyRepositoryModule {
  lazy val myRepository: MyRepository = new MyRepositoryImpl
}

trait MyRepository {
  def read(id: Long): Option[MyData]

  def write(data: MyData): Unit
}

class MyRepositoryImpl extends MyRepository {
  val fakeDb = mutable.Map[Long, MyData]()

  override def read(id: Long): Option[MyData] =
    fakeDb.get(id)

  override def write(data: MyData): Unit =
    fakeDb.put(data.id, data)
}