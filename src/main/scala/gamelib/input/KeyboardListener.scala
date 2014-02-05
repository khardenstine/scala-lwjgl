package gamelib.input

trait KeyboardListener {
	val keyCode: Int
	val eventState: EventKeyState.Value
	val repetition: Repetition.Value

	protected def handle(keyState: Boolean): Unit

	/**
	 *
	 * @return true if we don't need to keep listening
	 */
	final def heard(keyState: Boolean): Boolean = {
		handle(keyState: Boolean)
		repetition match {
			case Repetition.FOREVER => false
			case Repetition.ONCE => true
		}
	}

	def destroy() = {}

	final def isApplicable(keyState: Boolean): Boolean = {
		eventState match {
			case EventKeyState.BOTH => true
			case EventKeyState.DOWN => keyState
			case EventKeyState.RELEASED => !keyState
		}
	}
}

abstract class BothListener extends KeyboardListener {
	protected def handle(keyState: Boolean) = {
		if (keyState) {
			down()
		} else {
			released()
		}
	}

	protected def down(): Unit
	protected def released(): Unit

	val eventState = EventKeyState.BOTH
}

abstract class StatefulBothListener extends BothListener {
	private var heardDown: Boolean = false

	override protected def handle(keyState: Boolean) = {
		if (heardDown) {
			super.handle(keyState)
		} else if (keyState) {
			down()
			heardDown = true
		} else {
			println("Ignoring release of key " + keyCode)
		}
	}
}

abstract class ForeverDown(val keyCode: Int) extends KeyboardListener {
	val eventState = EventKeyState.DOWN
	val repetition = Repetition.FOREVER
}

abstract class ForeverReleased(val keyCode: Int) extends KeyboardListener {
	val eventState = EventKeyState.RELEASED
	val repetition = Repetition.FOREVER
}

abstract class OnceDown(val keyCode: Int) extends KeyboardListener {
	val eventState = EventKeyState.DOWN
	val repetition = Repetition.ONCE
}

abstract class OnceReleased(val keyCode: Int) extends KeyboardListener {
	val eventState = EventKeyState.RELEASED
	val repetition = Repetition.ONCE
}