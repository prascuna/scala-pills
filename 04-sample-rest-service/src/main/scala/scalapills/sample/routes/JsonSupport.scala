package scalapills.sample.routes


import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.AutoDerivation

trait JsonSupport extends FailFastCirceSupport with AutoDerivation {

}
