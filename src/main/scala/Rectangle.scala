import org.lwjgl.opengl.GL11
import scala.util.Random

// TODO initTime shouldn't be required
class Rectangle(protected val initTime: Long) extends Entity(100, 100) with Animated
{
	private val width: Float = 300
	private val height: Float = 225
	protected var rgb: (Float, Float, Float) = (0, 0, 0)
	protected val animationStepLength: Long = 1000

	def draw(time: Long): Unit = {
		animate(time)

		// set the color of the quad (R,G,B,A)
		(GL11.glColor3f(_, _, _)).tupled(rgb)

		// draw quad
		GL11.glBegin(GL11.GL_QUADS)
		GL11.glVertex2f(position.getX, position.getY)
		GL11.glVertex2f(position.getX + width, position.getY)
		GL11.glVertex2f(position.getX + width, position.getY + height)
		GL11.glVertex2f(position.getX, position.getY + height)
		GL11.glEnd()
	}

	protected def animationStep(): Unit = {
		rgb = (Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
		position.setLocation(position.getX + 20, position.getY + 20)
	}
}
