package scalapills.di.v04.traits.repositories

import scala.collection.mutable
import scalapills.di.v04.traits.models.MyData

trait MyRepository {
  def read(id: Long): Option[MyData]

  def write(data: MyData): Unit
}

trait MyRepositoryImpl extends MyRepository {
  val fakeDb = mutable.Map[Long, MyData]()

  override def read(id: Long): Option[MyData] =
    fakeDb.get(id)

  override def write(data: MyData): Unit =
    fakeDb.put(data.id, data)
}