package gamelib

trait Animation
{
	def animate(time: Long): Unit
}

trait StepAnimation extends Animation
{
	protected val animationStepLength: Long
	private var lastStepTime: Option[Long] = None

	def animate(time: Long): Unit = {
		var diff = time - lastStepTime.getOrElse(time - animationStepLength)
		if (diff >= animationStepLength)
		{
			lastStepTime = Some(time)

			while (diff >= animationStepLength)
			{
				animationStep()
				diff -= animationStepLength
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
