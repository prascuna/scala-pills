package scalapills.sample.models

import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
case class MyData(id: Option[Int], thisWasCamelCase: String)

@ConfiguredJsonCodec
case class Whatever(number: Int, somethingElse: String)

@ConfiguredJsonCodec
case class Test(camelCase: String, anotherCamelCase: List[Whatever], anOption: Option[String]= None)
