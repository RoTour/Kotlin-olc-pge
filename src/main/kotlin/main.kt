import java.awt.Color

const val NB_TILES_X = 15
const val NB_TILES_Y = 10

internal class Example(title: String, width: Int, height: Int, scaleX: Int, scaleY: Int) :
    PixelGameEngine(title, width, height, scaleX, scaleY) {
    override fun onUserCreate(): Boolean {
        for(i in 16 until 17*NB_TILES_X step 17){
            drawLine(i, 0, i, screenHeight(),Color.black)
            drawLine(0, i, screenWidth(), i,Color.black)
        }
        val posX = 3
        val posY = 4
        drawRect(17*posX,17*posY, 15,15, Color.red)
        return true
    }

    override fun onUserUpdate(fElapsedTime: Float): Boolean {

        return true
    }
}

fun main() {
    val ex = Example("Incredible App", 17*NB_TILES_X+2, 17*NB_TILES_Y+8, 4, 4)
    ex.start()
}