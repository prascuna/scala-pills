package scalapills.di.v04.traits.services

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSpec, Matchers}

import scalapills.di.v04.traits.models.MyData
import scalapills.di.v04.traits.repositories.MyRepository

class MyServiceImplTest extends FunSpec with Matchers with MockitoSugar {


  describe("MyServiceImpl") {
    describe("when using a stub") {
      trait MyRepositoryStub extends MyRepository {

        override def read(id: Long): Option[MyData] = None

        override def write(data: MyData): Unit = {
          println("some side effect")
          ()
        }

      }
      val service = new MyServiceImpl with MyRepositoryStub

      it("should do something") {
        service.doSomething(MyData(1, "ciao")) shouldBe None
        // How to verify that "read" method has been called? Check Thin Cake pattern test
      }
    }
  }
}
