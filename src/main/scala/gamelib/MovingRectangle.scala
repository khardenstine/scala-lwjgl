package gamelib

class MovingRectangle extends Rectangle
{
	private var acceleration_xy = 0
	private var acceleration_y = 0
	private var velocity_x = 0
	private var velocity_y = 0

	override protected def getAnimations: Seq[Animation] = {
		super.getAnimations ++ Seq(
			new Animation {
				def animate(time: Long) = {
					position.setLocation(position.getX + 1, position.getY + 1)
				}
			}
		)
	}
}
