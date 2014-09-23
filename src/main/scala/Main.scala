import org.lwjgl.examples.spaceinvaders
import pacman.Pacman

object Main {
  def main(args: Array[String]) {
    new Pacman().run()
  }

  def spaceInvaders() {
    spaceinvaders.Game.main(new Array[String](0))
  }
}
