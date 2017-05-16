package scalapills.dockerit.exceptions

sealed abstract class MyException(message: String) extends Exception(message)

case object DuplicatedEntryException extends MyException("An entry with the same id already exists in the database")