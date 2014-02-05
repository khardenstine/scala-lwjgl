package gamelib.animations

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
