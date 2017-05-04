package replacer.services

trait StringReplacer {

  def replace(text: String, oldString: String, newString: String): String
}

class StringReplacerImpl extends StringReplacer {
  def replace(text: String, oldString: String, newString: String): String =
    text.replace(oldString, newString)

}
