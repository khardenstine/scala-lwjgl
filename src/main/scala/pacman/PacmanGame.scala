package pacman

import gamelib.Game
import gamelib.input._
import scala.collection.mutable
import gamelib.entities.IEntity
import org.lwjgl.examples.spaceinvaders.TextureLoader
import org.lwjgl.input.Keyboard
import org.lwjgl.util.Point
import pacman.entity.Pacman

class PacmanGame extends Game {
  val displayTitle = "Pac-man"
  val displayWidth = 224
  val displayHeight = 288

  private val entities: mutable.MutableList[IEntity] = mutable.MutableList.empty
  private val textureLoader: TextureLoader = new TextureLoader
  private val inputListeners = new InputListenerRegistry

  override protected def init() {
    super.init()

    inputListeners.addListeners(
      KeyboardListener(
        Keyboard.KEY_ESCAPE,
        EventKeyState.BOTH,
        Repetition.FOREVER,
        _ => shutdown()
      ),
      new ForeverDown(Keyboard.KEY_A) {
        def handle(keyState: Boolean) = entities += new Pacman(new Point(100, 100), textureLoader)
      },
      new ForeverDown(Keyboard.KEY_D) {
        def handle(keyState: Boolean) = {
          entities.foreach(_.destroy())
          entities.clear()
        }
      }
    )
  }

  protected def handleKeyboard(time: Long, eventKey: Int, eventKeyState: Boolean): Unit = {
    inputListeners.handleInput(eventKey, eventKeyState)
  }

  protected def redraw(time: Long): Unit = {
    entities.foreach(_.draw(time))
  }
}

object PacmanGame {
  final val SPRITE_SHEET_LOC = "pacman/sprites.png"
}
