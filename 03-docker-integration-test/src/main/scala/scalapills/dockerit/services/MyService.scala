package scalapills.dockerit.services

import scala.concurrent.Future
import scalapills.dockerit.exceptions.MyException
import scalapills.dockerit.models.MyData
import scalapills.dockerit.repositories.MyRepository

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
