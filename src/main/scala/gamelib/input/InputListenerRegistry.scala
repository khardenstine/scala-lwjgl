package gamelib.input

import scala.collection.mutable

class InputListenerRegistry
{
	// TODO the key and eventstate should be hashed together for the key
	private val inputListeners = new mutable.HashMap[Int, mutable.Set[KeyboardListener]] with mutable.MultiMap[Int, KeyboardListener]

	def addListener(listener: KeyboardListener) = {
		inputListeners.addBinding(listener.key, listener)
	}

	def handleInput(keyCode: Int, keyState: Boolean) = {
		inputListeners.get(keyCode).foreach {
			set =>
				set.filter(_.isApplicable(keyState)).foreach {
					listener =>
						if (listener.heard()) {
							inputListeners.removeBinding(keyCode, listener)
						}
				}
		}
	}
}

trait KeyboardListener {
	val key: Int
	val eventState: EventKeyState.Value
	val repetition: Repetition.Value

	protected def handle(): Unit

	/**
	 *
	 * @return true if we don't need to keep listening
	 */
	def heard(): Boolean = {
		handle()
		repetition match {
			case Repetition.FOREVER => false
			case Repetition.ONCE => true
		}
	}

	def isApplicable(keyState: Boolean): Boolean = {
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
