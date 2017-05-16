package scalapills.dockerit.repositories

import scalapills.dockerit.config.{AppConfig, DbConfig}


trait DatabaseAccess {

  val dbConfig: DbConfig

  def db: dbConfig.profile.api.Database

}

object DatabaseAccess {
  def apply(dbConfig: DbConfig)(migration: (DbConfig) => Unit): DatabaseAccess = new JdbcDatabaseAccess(dbConfig)(migration)
}


class JdbcDatabaseAccess(val dbConfig: DbConfig)(migration: (DbConfig) => Unit) extends DatabaseAccess {

  val profile = dbConfig.profile

  migration(dbConfig)

  override def db: profile.api.Database = {
    val tsConfig = AppConfig.asTypeSafeConfig(dbConfig)
    profile.api.Database.forConfig("", tsConfig)
  }

}