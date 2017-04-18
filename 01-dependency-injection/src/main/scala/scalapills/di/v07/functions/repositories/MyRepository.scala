package scalapills.di.v07.functions.repositories

import scala.collection.mutable
import scalapills.di.v07.functions.models.MyData


object MyRepository {
  val fakeDb = mutable.Map[Long, MyData]()

  def read(id: Long): Option[MyData] =
    fakeDb.get(id)

  def write(data: MyData): Unit =
    fakeDb.put(data.id, data)
}
