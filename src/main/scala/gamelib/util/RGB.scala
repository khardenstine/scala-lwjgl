package gamelib.util

// todo sanitize values
class RGB(
	private var red: Float,
	private var green: Float,
	private var blue: Float
){
	def setRed(value: Float) = red = value
	def setGreen(value: Float) = green = value
	def setBlue(value: Float) = blue = value
	def setAll(red: Float, green: Float, blue: Float): Unit = {
		setRed(red)
		setGreen(green)
		setBlue(blue)
	}

	def getRed: Float = red
	def getGreen: Float = green
	def getBlue: Float = blue

	def getTuple: (Float, Float, Float) = {
		(red, green, blue)
	}

	def applyValues[T](fun: (Float, Float, Float) => T): T = {
		fun(red, green, blue)
	}
}
