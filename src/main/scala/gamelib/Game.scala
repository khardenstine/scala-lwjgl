package gamelib

import org.lwjgl.input.Keyboard
import org.lwjgl.{Sys, LWJGLException}
import org.lwjgl.opengl.{GL11, DisplayMode, Display}

trait Game {
	private var isRunning: Boolean = false

	val displayTitle: String
	val displayWidth: Int
	val displayHeight: Int

	private def gameLoop(): Unit = {
		while(isRunning && !Display.isCloseRequested) {
			// Clear the screen and depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)

			GL11.glMatrixMode(GL11.GL_MODELVIEW)
			GL11.glLoadIdentity()
			Display.sync(60)

			val time = getTime

      while(Keyboard.next()) {
        handleKeyboard(time, Keyboard.getEventKey, Keyboard.getEventKeyState)
      }

      redraw(time)

			Display.update()
		}
	}

  protected def handleKeyboard(time: Long, eventKey: Int, eventKeyState: Boolean): Unit

  protected def redraw(time: Long): Unit

	private def getTime: Long = {
		(Sys.getTime * 1000) / Sys.getTimerResolution
	}

	protected def init() {
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
	}

	protected def destroy() {
		Display.destroy()
	}

	protected final def shutdown() {
		isRunning = false
		System.out.println("Shutting down.")
	}

	def run() {
		init()
		gameLoop()
		destroy()
	}

	def terminate() {
	    shutdown()
		// log termination
	}
}
