package replacer.services

import replacer.config.ReplacerConfig

import scala.concurrent.{ExecutionContext, Future}

trait ReplacerService {
  def replace(oldString: String, newString: String): Future[String]
}


class ReplacerServiceImpl(config: ReplacerConfig, fetchService: FetchService, stringReplacer: StringReplacer)(implicit ec: ExecutionContext) extends ReplacerService {
  override def replace(oldString: String, newString: String): Future[String] =
    fetchService.fetch(config.method, config.uri).map(stringReplacer.replace(_, oldString, newString))
}
