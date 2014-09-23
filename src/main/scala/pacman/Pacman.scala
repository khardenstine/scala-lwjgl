package pacman

import gamelib.Game
import gamelib.input.{Repetition, EventKeyState, KeyboardListener, InputListenerRegistry}
import scala.collection.mutable
import gamelib.entities.{Sprite, IEntity}
import org.lwjgl.examples.spaceinvaders.TextureLoader
import org.lwjgl.input.Keyboard
import org.lwjgl.util.Point

class Pacman extends Game {
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
      KeyboardListener(
        Keyboard.KEY_A,
        EventKeyState.DOWN,
        Repetition.FOREVER,
        _ => entities += new Sprite(new Point(100, 100), textureLoader, "spaceinvaders/ship.gif")
      ),
      KeyboardListener(
        Keyboard.KEY_D,
        EventKeyState.DOWN,
        Repetition.FOREVER,
        _ => {
          entities.foreach(_.destroy())
          entities.clear()
        }
      )
    )
  }

  protected def handleKeyboard(time: Long, eventKey: Int, eventKeyState: Boolean): Unit = {
    inputListeners.handleInput(eventKey, eventKeyState)
  }

  protected def redraw(time: Long): Unit = {
    entities.foreach(_.draw(time))
  }
}
