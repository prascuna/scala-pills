package scalapills.sample.repositories

import org.flywaydb.core.Flyway

import scalapills.sample.config.DbConfig

class FlywayRunner(dbConfig: DbConfig) {

  private val flyway = new Flyway()

  flyway.setDataSource(
    dbConfig.uri.toString(),
    dbConfig.user,
    dbConfig.password
  )

  def migrate = flyway.migrate()
}

object FlywayRunner {
  def apply(dbConfig: DbConfig) = new FlywayRunner(dbConfig)
}

