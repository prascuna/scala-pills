package scalapills.di.v05.thincake.services

import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSpec, Matchers}

import scalapills.di.v05.thincake.models.MyData
import scalapills.di.v05.thincake.repositories.MyRepository

class MyServiceModuleTest extends FunSpec with Matchers with MockitoSugar {



  describe("MyService") {

    describe("when testing the result") {
      val repositoryMock = mock[MyRepository]
      when(repositoryMock.read(anyLong)) thenReturn None

      trait MyServiceTestModule extends MyServiceModule {
        override val myService = new MyServiceImpl(repositoryMock)
      }

      object TestingModule extends MyServiceTestModule

      it("should do something") {
        TestingModule.myService.doSomething(MyData(1, "ciao")) shouldBe None
      }
    }


    describe("when verifying the invokation") {
      val repositoryMock = mock[MyRepository]

      trait MyServiceTestModule extends MyServiceModule {
        override val myService = new MyServiceImpl(repositoryMock)
      }

      object TestingModule extends MyServiceTestModule
      it("should invoke the repository") {
        TestingModule.myService.doSomething(MyData(1, "ciao"))

        verify(repositoryMock).read(1)
      }
    }
  }

}
