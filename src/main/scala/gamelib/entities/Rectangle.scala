package gamelib.entities

import org.lwjgl.opengl.GL11
import gamelib.animations.{Animation, RandomColor}
import gamelib.util.RGB

class Rectangle extends Entity(100, 100) with MultiAnimated {
  private val width: Float = 25
  private val height: Float = 25
  private val rgb: RGB = new RGB(0, 0, 90000000)

  def draw(time: Long): Unit = {
    animate(time)

    // set the color of the quad (R,G,B,A)
    rgb.applyValues(GL11.glColor3f)

    // draw quad
    GL11.glBegin(GL11.GL_QUADS)
    GL11.glVertex2f(position.getX, position.getY)
    GL11.glVertex2f(position.getX + width, position.getY)
    GL11.glVertex2f(position.getX + width, position.getY + height)
    GL11.glVertex2f(position.getX, position.getY + height)
    GL11.glEnd()
  }

  protected def getAnimations: Seq[Animation] = Seq(
    new RandomColor(rgb)
  )

  def destroy() = {}
}
