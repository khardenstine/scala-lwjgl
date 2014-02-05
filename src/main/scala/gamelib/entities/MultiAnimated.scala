package gamelib.entities

import gamelib.animations.Animation

trait MultiAnimated
{
	private lazy val animations: Seq[Animation] = getAnimations

	def animate(time: Long): Unit = {
		animations.foreach(_.animate(time))
	}

	protected def getAnimations: Seq[Animation]
}
