package gamelib.entities

import org.lwjgl.examples.spaceinvaders.Texture
import org.lwjgl.util.Point
import gamelib.animations.{StepAnimation, Animation}

abstract class AnimatedSprite(position: Point, texture: Texture) extends Sprite(position, texture) with MultiAnimated {

  protected val textureAnimation: StepAnimation

  override def draw(time: Long): Unit = {
    animate(time)
    super.draw(time)
  }

  protected def getAnimations: List[Animation] = List(textureAnimation)
}



