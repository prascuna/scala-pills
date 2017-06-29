package scalapills.sample.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

import scalapills.sample.models.MyData


trait JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val myDataFormatter = jsonFormat2(MyData)
}
