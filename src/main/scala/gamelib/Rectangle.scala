package gamelib

import org.lwjgl.opengl.GL11
import scala.util.Random

class Rectangle extends Entity(100, 100) with MultiAnimated
{
	private val width: Float = 300
	private val height: Float = 225
	protected var rgb: (Float, Float, Float) = (0, 0, 0)

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

	protected val animations = Seq(
		new Animation {
			def animate(time: Long) = position.setLocation(position.getX + 1, position.getY + 1)
		},
		new StepAnimation {
			protected val stepLength = 1000L
			protected def animationStep() = rgb = (Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
		}
	)
}
