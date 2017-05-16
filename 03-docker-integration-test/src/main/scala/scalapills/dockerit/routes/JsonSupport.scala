package scalapills.dockerit.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

import scalapills.dockerit.models.MyData


trait JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val myDataFormatter = jsonFormat2(MyData)
}
