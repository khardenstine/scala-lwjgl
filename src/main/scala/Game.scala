import org.lwjgl.input.Keyboard
import org.lwjgl.{Sys, LWJGLException}
import org.lwjgl.opengl.{GL11, DisplayMode, Display}
import scala.collection.mutable

class Game {
	private var isRunning: Boolean = false

	private val entities: mutable.MutableList[Entity] = mutable.MutableList.empty

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
			if (Keyboard.getEventKey == Keyboard.KEY_ESCAPE) {
				shutdown()
			}
			if (Keyboard.getEventKey == Keyboard.KEY_A && Keyboard.getEventKeyState) {
				entities += new Rectangle
			}
		}
	}


	def getTime: Long = {
		(Sys.getTime * 1000) / Sys.getTimerResolution
	}

	private def init(): Unit = {
		isRunning = true
		System.out.println("Starting up.")

		try {
			Display.setDisplayMode(new DisplayMode(800, 600))
			Display.setTitle("HW")
			Display.create()
		} catch	{
			case e: LWJGLException => {
				System.out.println(e.getMessage)
				e.printStackTrace()
				System.exit(0)
			}
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION)
		GL11.glLoadIdentity()
		GL11.glOrtho(0, 800, 0, 600, 1, -1)
		GL11.glMatrixMode(GL11.GL_MODELVIEW)
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
