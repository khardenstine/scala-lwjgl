package gamelib.input

import scala.collection.mutable

class InputListenerRegistry
{
	// TODO the key and eventstate should be hashed together for the key
	private val inputListeners = new mutable.HashMap[Int, mutable.Set[KeyboardListener]] with mutable.MultiMap[Int, KeyboardListener]

	def addListener(listener: KeyboardListener) = {
		inputListeners.addBinding(listener.keyCode, listener)
	}

	def addListeners(listeners: KeyboardListener*) = {
		listeners.foreach(addListener)
	}

	def destroyListener(listener: KeyboardListener) = {
		listener.destroy()
		inputListeners.removeBinding(listener.keyCode, listener)
	}

	def destroyListeners(listeners: KeyboardListener*) = {
		listeners.foreach(destroyListener)
	}

	def handleInput(keyCode: Int, keyState: Boolean) = {
		inputListeners.get(keyCode).foreach {
			set =>
				set.filter(_.isApplicable(keyState)).foreach {
					listener =>
						if (listener.heard()) {
							destroyListener(listener)
						}
				}
		}
	}
}

trait KeyboardListener {
	val keyCode: Int
	val eventState: EventKeyState.Value
	val repetition: Repetition.Value
	private var _destroy = false

	protected def handle(): Unit

	/**
	 *
	 * @return true if we don't need to keep listening
	 */
	final def heard(): Boolean = {
		if (_destroy) {
			sys.error("destroyed listener has been heard")
			true
		} else {
			handle()
			repetition match {
				case Repetition.FOREVER => false
				case Repetition.ONCE => true
			}
		}
	}

	final def destroy() = {
		_destroy = true
	}

	final def isApplicable(keyState: Boolean): Boolean = {
		eventState match {
			case EventKeyState.BOTH => true
			case EventKeyState.DOWN => keyState
			case EventKeyState.RELEASED => !keyState
		}
	}
}

object Repetition extends Enumeration {
	val ONCE, FOREVER = Value
}

object EventKeyState extends Enumeration {
	val DOWN, RELEASED, BOTH = Value
}

abstract class ForeverDown(val keyCode: Int) extends KeyboardListener {
	val eventState = EventKeyState.DOWN
	val repetition = Repetition.FOREVER
}

abstract class ForeverReleased(val keyCode: Int) extends KeyboardListener {
	val eventState = EventKeyState.RELEASED
	val repetition = Repetition.FOREVER
}
