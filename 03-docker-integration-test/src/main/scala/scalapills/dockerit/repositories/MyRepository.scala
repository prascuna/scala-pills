package scalapills.dockerit.repositories

import java.sql.SQLIntegrityConstraintViolationException

import scala.concurrent.{ExecutionContext, Future}
import scalapills.dockerit.exceptions.{DuplicatedEntryException, MyException}
import scalapills.dockerit.models.MyData

trait MyRepository {
  def read(id: Int): Future[Option[MyData]]

  def write(data: MyData): Future[Either[MyException, MyData]]

  def truncate: Future[Unit]
}

object MyRepository {
  def apply(dbAccess: DatabaseAccess)(implicit ec: ExecutionContext): MyRepository = new SlickMyRepository(dbAccess)

}

class SlickMyRepository(dbAccess: DatabaseAccess)(implicit ec: ExecutionContext) extends MyRepository {
  val profile = dbAccess.dbConfig.profile

  import profile.api._

  class MyDataTable(tag: Tag) extends Table[MyData](tag, "mydata") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id.?, name) <> (MyData.tupled, MyData.unapply)

  }


  private val db = dbAccess.db
  private val myData = TableQuery[MyDataTable]

  override def read(id: Int): Future[Option[MyData]] =
    db.run {
      myData.filter(_.id === id).result.headOption
    }

  override def write(data: MyData): Future[Either[MyException, MyData]] =
    db.run {
      myData returning myData.map(_.id) forceInsert data
    }.map { dbId =>
      Right(data.copy(id = Some(dbId)))
    }.recover {
      case _: SQLIntegrityConstraintViolationException => Left(DuplicatedEntryException)
    }

  override def truncate: Future[Unit] =
    db.run {
      myData.schema.truncate
    }
}