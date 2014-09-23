package pacman

import gamelib.Game
import gamelib.input.{Repetition, EventKeyState, KeyboardListener, InputListenerRegistry}
import scala.collection.mutable
import gamelib.entities.{Sprite, IEntity}
import org.lwjgl.examples.spaceinvaders.TextureLoader
import org.lwjgl.input.Keyboard
import org.lwjgl.util.Point

class Pacman extends Game
{
	val displayTitle = "Pac-man"
	val displayWidth = 224
	val displayHeight = 288

  private val entities: mutable.MutableList[IEntity] = mutable.MutableList.empty
  private val textureLoader: TextureLoader = new TextureLoader
  private val inputListeners = new InputListenerRegistry

  override protected def init() {
    super.init()

    inputListeners.addListeners(new KeyboardListener {
      protected def handle(keyState: Boolean) = shutdown()

      val eventState = EventKeyState.BOTH
      val keyCode = Keyboard.KEY_ESCAPE
      val repetition = Repetition.FOREVER
    },
    new KeyboardListener {
      protected def handle(keyState: Boolean) = {
        //entities += new MovingRectangle(inputListeners)
        entities += new Sprite(new Point(100, 100), textureLoader, "spaceinvaders/ship.gif")
      }

      val eventState = EventKeyState.DOWN
      val keyCode = Keyboard.KEY_A
      val repetition = Repetition.FOREVER
    },
    new KeyboardListener {
      protected def handle(keyState: Boolean) = {
        entities.foreach(_.destroy())
        entities.clear()
      }

      val eventState = EventKeyState.DOWN
      val keyCode = Keyboard.KEY_D
      val repetition = Repetition.FOREVER
    })
  }

  protected def handleKeyboard(time: Long, eventKey: Int, eventKeyState: Boolean): Unit = {
    inputListeners.handleInput(eventKey, eventKeyState)
  }

  protected def redraw(time: Long): Unit = {
    entities.foreach(_.draw(time))
  }
}
