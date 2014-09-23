package gamelib.input

import scala.collection.mutable

class InputListenerRegistry {
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
            if (listener.heard(keyState)) {
              destroyListener(listener)
            }
        }
    }
  }
}
