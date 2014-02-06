package gamelib

import gamelib.input.{Repetition, EventKeyState, KeyboardListener, InputListenerRegistry}
import gamelib.entities.{Entity, MovingRectangle}
import org.lwjgl.input.Keyboard
import org.lwjgl.{Sys, LWJGLException}
import org.lwjgl.opengl.{GL11, DisplayMode, Display}
import scala.collection.mutable

abstract class Game {
	private var isRunning: Boolean = false

	private val entities: mutable.MutableList[Entity] = mutable.MutableList.empty

	private val inputListeners = new InputListenerRegistry

	private def gameLoop(): Unit = {
		while(isRunning && !Display.isCloseRequested) {
			// Clear the screen and depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)

			GL11.glMatrixMode(GL11.GL_MODELVIEW)
			GL11.glLoadIdentity()
			Display.sync(60)

			val time = getTime

			handleKeyboard(time)

			entities.foreach(_.draw(time))

			Display.update()
		}
	}

	private def handleKeyboard(time: Long): Unit = {
		while(Keyboard.next()) {
			inputListeners.handleInput(Keyboard.getEventKey, Keyboard.getEventKeyState)
		}
	}

	def getTime: Long = {
		(Sys.getTime * 1000) / Sys.getTimerResolution
	}

	val displayTitle: String
	val displayWidth: Int
	val displayHeight: Int

	private def init(): Unit = {
		isRunning = true
		System.out.println("Starting up.")

		try {
			Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight))
			Display.setTitle(displayTitle)
			Display.create()
		} catch	{
			case e: LWJGLException => {
				System.out.println(e.getMessage)
				e.printStackTrace()
				System.exit(0)
			}
		}

		// enable textures since we're going to use these for our sprites
		GL11.glEnable(GL11.GL_TEXTURE_2D)

		// disable the OpenGL depth test since we're rendering 2D graphics
		GL11.glDisable(GL11.GL_DEPTH_TEST)

		GL11.glMatrixMode(GL11.GL_PROJECTION)
		GL11.glLoadIdentity()
		GL11.glOrtho(0, displayWidth, 0, displayHeight, 1, -1)
		GL11.glMatrixMode(GL11.GL_MODELVIEW)

		inputListeners.addListener(new KeyboardListener {
			protected def handle(keyState: Boolean) = shutdown()

			val eventState = EventKeyState.BOTH
			val keyCode = Keyboard.KEY_ESCAPE
			val repetition = Repetition.FOREVER
		})

		inputListeners.addListeners(new KeyboardListener {
			protected def handle(keyState: Boolean) = {
				entities += new MovingRectangle(inputListeners)
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

	private def destroy(): Unit = {
		Display.destroy()
	}

	private def shutdown(): Unit = {
		isRunning = false
		System.out.println("Shutting down.")
	}

	def run(): Unit = {
		init()
		gameLoop()
		destroy()
	}

	def terminate(): Unit = {
	    shutdown()
		// log termination
	}
}
