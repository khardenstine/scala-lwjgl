package gamelib.animations

import gamelib.StepAnimation
import scala.util.Random

class RandomColor(rgb: RGB) extends StepAnimation {
	protected val stepLength = 1000L
	protected def animationStep() = {
		rgb.setAll(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
	}
}
