
import PixelGameEngine
import java.awt.Color


internal class Example(title: String?, width: Int, height: Int, scaleX: Int, scaleY: Int) :
    PixelGameEngine("Example", width, height, scaleX, scaleY) {
    override fun OnUserCreate(): Boolean {
        return true
    }

    override fun OnUserUpdate(fElapsedTime: Float): Boolean {
        return true
    }
}

fun main(args: Array<String>) {
    val ex = Example("Example Title", 800, 600, 2, 2)
    ex.start()
}