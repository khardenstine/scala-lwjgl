package gamelib.entities

import org.lwjgl.input.Keyboard
import gamelib.animations.Animation
import gamelib.input.{Repetition, StatefulBothListener, InputListenerRegistry}

class MovingRectangle(inputListener: InputListenerRegistry) extends Rectangle
{
	private var velocity_x: Float = 0
	private var velocity_y: Float = 0

	private var goal_velocity_x: Float = 0
	private var goal_velocity_y: Float = 0

	val TERMINAL_VELOCITY: Float = 6
	val ACCELERATION: Float = 0.5F

	private val listeners = Seq(
		new StatefulBothListener {
			val keyCode = Keyboard.KEY_RIGHT
			val repetition = Repetition.FOREVER
			protected def down() =		goal_velocity_x += TERMINAL_VELOCITY
			protected def released() =	goal_velocity_x -= TERMINAL_VELOCITY
		},
		new StatefulBothListener {
			val keyCode = Keyboard.KEY_LEFT
			val repetition = Repetition.FOREVER
			protected def down() =		goal_velocity_x -= TERMINAL_VELOCITY
			protected def released() =	goal_velocity_x += TERMINAL_VELOCITY
		},
		new StatefulBothListener {
			val keyCode = Keyboard.KEY_UP
			val repetition = Repetition.FOREVER
			protected def down() =		goal_velocity_y += TERMINAL_VELOCITY
			protected def released() =	goal_velocity_y -= TERMINAL_VELOCITY
		},
		new StatefulBothListener {
			val keyCode = Keyboard.KEY_DOWN
			val repetition = Repetition.FOREVER
			protected def down() =		goal_velocity_y -= TERMINAL_VELOCITY
			protected def released() =	goal_velocity_y += TERMINAL_VELOCITY
		}
	)

	inputListener.addListeners(listeners:_*)

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

	override def destroy() = {
		super.destroy()
		inputListener.destroyListeners(listeners:_*)
	}
}
