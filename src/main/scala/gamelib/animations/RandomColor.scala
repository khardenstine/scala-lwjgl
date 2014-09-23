package gamelib.animations

import scala.util.Random
import gamelib.util.RGB

class RandomColor(rgb: RGB) extends StepAnimation {
  protected val stepLength = 1000L

  protected def animationStep() = {
    rgb.setAll(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
  }
}
