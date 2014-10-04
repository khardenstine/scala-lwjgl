import org.lwjgl.examples.spaceinvaders
import pacman.PacmanGame

object Main {
  def main(args: Array[String]) {
    new PacmanGame().run()
  }

  def spaceInvaders() {
    spaceinvaders.Game.main(new Array[String](0))
  }
}
