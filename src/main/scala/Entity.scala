import org.lwjgl.util.Point

trait IEntity
{
	protected val position: Point
	def draw(time: Long)
}

// TODO replace position with more accurate solution
// e.g. Point(double, double){
//  	def  getIntPoint()
// }
abstract class Entity(val position: Point) extends IEntity
{
	def this(x: Int, y: Int) {
		this(new Point(x, y))
	}
}

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
