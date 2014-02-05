package gamelib

import org.lwjgl.input.Keyboard
import gamelib.input.{ForeverReleased, ForeverDown, InputListenerRegistry}

class MovingRectangle(inputListener: InputListenerRegistry) extends Rectangle
{
	private var velocity_x: Float = 0
	private var velocity_y: Float = 0

	private var goal_velocity_x: Float = 0
	private var goal_velocity_y: Float = 0

	val TERMINAL_VELOCITY: Float = 6
	val ACCELERATION: Float = 0.5F

	inputListener.addListeners(
		new ForeverDown(Keyboard.KEY_RIGHT) {
			protected def handle() = goal_velocity_x += TERMINAL_VELOCITY
		},
		new ForeverDown(Keyboard.KEY_LEFT) {
			protected def handle() = goal_velocity_x -= TERMINAL_VELOCITY
		},
		new ForeverDown(Keyboard.KEY_UP) {
			protected def handle() = goal_velocity_y += TERMINAL_VELOCITY
		},
		new ForeverDown(Keyboard.KEY_DOWN) {
			protected def handle() = goal_velocity_y -= TERMINAL_VELOCITY
		},
		new ForeverReleased(Keyboard.KEY_RIGHT) {
			protected def handle() = goal_velocity_x -= TERMINAL_VELOCITY
		},
		new ForeverReleased(Keyboard.KEY_LEFT) {
			protected def handle() = goal_velocity_x += TERMINAL_VELOCITY
		},
		new ForeverReleased(Keyboard.KEY_UP) {
			protected def handle() = goal_velocity_y -= TERMINAL_VELOCITY
		},
		new ForeverReleased(Keyboard.KEY_DOWN) {
			protected def handle() = goal_velocity_y += TERMINAL_VELOCITY
		}
	)

	override protected def getAnimations: Seq[Animation] = {
		super.getAnimations ++ Seq(
			new Animation {
				def animate(time: Long) = {
					velocity_x = accelerate(velocity_x, goal_velocity_x)
					velocity_y = accelerate(velocity_y, goal_velocity_y)
					position.setLocation((position.getX + velocity_x).toInt, (position.getY + velocity_y).toInt)
				}
			}
		)
	}

	private def accelerate(oldVelocity: Float, goalVelocity: Float): Float = {
		if (oldVelocity < goalVelocity) {
			val newVelocity = oldVelocity + ACCELERATION
			if (newVelocity >= goalVelocity)
				goalVelocity
			else
				newVelocity
		} else if (oldVelocity > goalVelocity) {
			val newVelocity = oldVelocity - ACCELERATION
			if (newVelocity <= goalVelocity)
				goalVelocity
			else
				newVelocity
		} else {
			goalVelocity
		}
	}
}
