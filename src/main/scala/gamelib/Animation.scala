package gamelib

trait Animation
{
	def animate(time: Long): Unit
}

trait StepAnimation extends Animation
{
	protected val stepLength: Long
	private var lastStepTime: Option[Long] = None

	def animate(time: Long): Unit = {
		var diff = time - lastStepTime.getOrElse(time - stepLength)
		if (diff >= stepLength)
		{
			lastStepTime = Some(time)

			while (diff >= stepLength)
			{
				animationStep()
				diff -= stepLength
			}
		}
	}

	protected def animationStep()
}

trait MultiAnimated
{
	protected val animations: Seq[Animation]

	def animate(time: Long): Unit = {
		animations.foreach(_.animate(time))
	}
}
