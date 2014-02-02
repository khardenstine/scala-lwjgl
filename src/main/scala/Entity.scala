import org.lwjgl.util.Point

trait IEntity
{
	protected val position: Point
	def draw(time: Long)
}

abstract class Entity(val position: Point) extends IEntity
{
	def this(x: Int, y: Int) {
		this(new Point(x, y))
	}
}

trait Animated
{
	protected val initTime: Long
	protected val animationStepLength: Long
	private var lastStepTime: Long = initTime

	def animate(time: Long): Unit = {
		var diff = time - lastStepTime
		if (diff >= animationStepLength)
		{
			lastStepTime = time

			while (diff >= animationStepLength)
			{
				animationStep()
				diff -= animationStepLength
			}
		}
	}

	protected def animationStep()
}
