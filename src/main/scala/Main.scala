import org.lwjgl.examples.spaceinvaders
import org.lwjgl.LWJGLException
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode

object Main {
	def main(args: Array[String]) {
		new Game().run()
	}

	def start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600))
			Display.setTitle("HW")
			Display.create()
		} catch	{
			case e: LWJGLException => {
				e.printStackTrace()
				System.exit(0)
			}
		}

		// init OpenGL here

		while (!Display.isCloseRequested) {
			Display.update()
		}

		Display.destroy()
	}

	def spaceInvaders() {
		spaceinvaders.Game.main(new Array[String](0))
	}
}
