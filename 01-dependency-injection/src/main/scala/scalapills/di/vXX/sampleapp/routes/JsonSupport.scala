package scalapills.di.vXX.sampleapp.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

import scalapills.di.vXX.sampleapp.models.MyData

trait JsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val myDataFormatter = jsonFormat2(MyData)
}
