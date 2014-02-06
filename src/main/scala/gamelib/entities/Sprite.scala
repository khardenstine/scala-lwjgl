package gamelib.entities

import org.lwjgl.examples.spaceinvaders.{TextureLoader, Texture}
import org.lwjgl.opengl.GL11
import org.lwjgl.util.Point

class Sprite(val position: Point, texture: Texture) extends IEntity
{
	def this (position: Point, loader: TextureLoader, ref: String) {
		this(position, loader.getTexture(ref))
	}

	def getWidth: Int = texture.getImageWidth
	def getHeight: Int = texture.getImageHeight

	def draw(time: Long): Unit = {
		GL11.glPushMatrix()
		texture.bind()
		GL11.glTranslatef(position.getX, position.getY, 0)
		GL11.glBegin(GL11.GL_QUADS)

		GL11.glTexCoord2f(0, 0)
		GL11.glVertex2f(0, 0)
		GL11.glTexCoord2f(0, texture.getHeight)
		GL11.glVertex2f(0, getHeight)
		GL11.glTexCoord2f(texture.getWidth, texture.getHeight)
		GL11.glVertex2f(getWidth, getHeight)
		GL11.glTexCoord2f(texture.getWidth, 0)
		GL11.glVertex2f(getWidth, 0)

		GL11.glEnd()
		GL11.glPopMatrix()
	}

	def destroy() = {}
}


