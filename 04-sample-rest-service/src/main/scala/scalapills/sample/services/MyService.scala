package scalapills.sample.services

import scala.concurrent.Future
import scalapills.sample.exceptions.MyException
import scalapills.sample.models.MyData
import scalapills.sample.repositories.MyRepository

trait MyService {

  def write(data: MyData): Future[Either[MyException, MyData]]

  def read(id: Int): Future[Option[MyData]]

}

object MyService {
  def apply(myRepository: MyRepository): MyService = new MyServiceImpl(myRepository)
}

class MyServiceImpl(repository: MyRepository) extends MyService {

  override def write(data: MyData): Future[Either[MyException, MyData]] =
    repository.write(data)

  override def read(id: Int): Future[Option[MyData]] =
    repository.read(id)

}
