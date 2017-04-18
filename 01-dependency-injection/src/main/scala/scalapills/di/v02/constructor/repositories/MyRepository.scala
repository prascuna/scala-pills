package scalapills.di.v02.constructor.repositories

import scala.collection.mutable
import scalapills.di.v02.constructor.models.MyData

trait MyRepository {
  def read(id: Long): Option[MyData]

  def write(data: MyData)
}

class MyRepositoryImpl extends MyRepository {
  val fakeDb = mutable.Map[Long, MyData]()

  override def read(id: Long): Option[MyData] =
    fakeDb.get(id)

  override def write(data: MyData): Unit =
    fakeDb.put(data.id, data)
}