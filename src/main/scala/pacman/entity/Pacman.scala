package pacman.entity

import gamelib.entities.AnimatedSprite
import org.lwjgl.util.Point
import org.lwjgl.examples.spaceinvaders.{Texture, TextureLoader}
import gamelib.animations.StepAnimation
import pacman.PacmanGame

class Pacman(position: Point, textureLoader: TextureLoader) extends {
  private val texture: Texture = {
    val _t = textureLoader.getTexture(PacmanGame.SPRITE_SHEET_LOC)
    _t.setHeight(16)
    _t.setWidth(16)
    _t.setXOffset(456)
    _t.setYOffset(0)
    _t
  }
} with AnimatedSprite(position, texture){
  protected val textureAnimation =  new StepAnimation {
    protected def animationStep(): Unit = {
      if (texture.getXOffset == 456) {
        texture.setXOffset(472)
      } else {
        texture.setXOffset(456)
      }
    }

    protected val stepLength: Long = 200L
  }
}
