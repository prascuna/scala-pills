package scalapills.sample.routes


import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Printer
import io.circe.generic.AutoDerivation

trait JsonSupport extends FailFastCirceSupport with AutoDerivation {
  // Uncomment this if you want the options = None to drop the key
  // implicit val printer = Printer.noSpaces.copy(dropNullKeys = true)
}
