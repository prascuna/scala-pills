package scalapills.di.v01.guice.modules

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import net.codingwell.scalaguice.ScalaModule


import scalapills.di.v01.guice.repositories.{MyRepository, MyRepositoryImpl}
import scalapills.di.v01.guice.services.{MyService, MyServiceImpl}

class MyModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[MyService].to[MyServiceImpl].in[Singleton]
    bind[MyRepository].to[MyRepositoryImpl].in[Singleton]
  }
}
