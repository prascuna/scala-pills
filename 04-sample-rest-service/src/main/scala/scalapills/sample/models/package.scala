package scalapills.sample

import io.circe.generic.extras.Configuration

package object models {
  implicit val config: Configuration = Configuration.default.withSnakeCaseKeys
}
