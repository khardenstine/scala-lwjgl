package gamelib.entities

import org.lwjgl.util.Point

trait IEntity
{
	protected val position: Point
	def draw(time: Long): Unit
	def destroy(): Unit
}

// TODO replace position with more accurate solution
// e.g. Point(double, double){
//  	def  getIntPoint()
// }
abstract class Entity(val position: Point) extends IEntity
{
	def this(x: Int, y: Int) {
		this(new Point(x, y))
	}
}
