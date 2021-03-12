

import PixelGameEngine.Sprite
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.ArrayList
import javax.imageio.ImageIO
import javax.swing.JFrame

/*
Copyright (c) 2021, Hakimen
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
3. All advertising materials mentioning features or use of this software
must display the following acknowledgement: This product includes software
developed by the Hakimen.
4. Neither the name of the Hakimen nor the names of its contributors may be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY HAKIMEN ''AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL HAKIMEN BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/
/**
 * **Hakimen's Game Engine**
 *
 * @author Hakimen
 * @portfrom olc::PixelGameEngine (by Javidx9)
 * @version 2.3
 */
/*
Example Implementation :
import java.awt.*;
public class ExampleClass {
    static class Example extends PixelGameEngine{
        public Example(String title,int width, int height,int scaleX,int scaleY) {
            super("Example", width, height, scaleX, scaleY);
        }
        @Override
        public boolean OnUserCreate() {
            return true;
        }

        @Override
        public boolean OnUserUpdate(float fElapsedTime) {
            return true;
        }
    }
    public static void main(String[] args) {
        Example ex = new Example("Example Title",800,600,2,2);
        ex.start();
    }
}
 */
abstract class PixelGameEngine {
    class Sprite {
        var img: BufferedImage? = null
        var sizeX = 0
        var sizeY = 0

        private constructor(path: String) {
            try {
                img = ImageIO.read(javaClass.getResource(path))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            sizeX = img!!.width
            sizeY = img!!.height
        }

        constructor(img: BufferedImage?) {
            this.img = img
        }

        companion object {
            fun load(path: String): Sprite {
                return Sprite(path)
            }
        }
    }

    private var mouseX = 0
    private var mouseY = 0
    private var nScrollDir = 0
    private val ScreenWidth: Int
    private val ScreenHeight: Int
    private val scaleX: Int
    private val scaleY: Int
    private val b_keys = BooleanArray(1024)
    private val b_mouse = BooleanArray(6)
    var frame: JFrame
    fun GetMouseScroll(): Int {
        return nScrollDir
    }

    fun GetKey(i: Int): Boolean {
        return b_keys[i]
    }

    fun GetMouse(i: Int): Boolean {
        return b_mouse[i]
    }

    abstract fun OnUserCreate(): Boolean
    abstract fun OnUserUpdate(fElapsedTime: Float): Boolean
    fun OnUserDestroy(): Boolean {
        return true
    }

    var display: View

    constructor(sTitle: String, maxFPS: Int, width: Int, height: Int, scaleX: Int, scaleY: Int) {
        PixelGameEngine.maxFPS = maxFPS
        title = sTitle
        title = title
        this.scaleX = scaleX
        this.scaleY = scaleY
        ScreenHeight = height * this.scaleY
        ScreenWidth = width * this.scaleX
        display = View()
        frame = JFrame(title)
        frame.layout = CardLayout()
        frame.add(display, "Hee", 0)
        frame.setSize(ScreenWidth, ScreenHeight)
        frame.setLocationRelativeTo(null)
        frame.isResizable = false
        frame.defaultCloseOperation = 3
        frame.isVisible = true
        display.engine = this
    }

    constructor(sTitle: String, width: Int, height: Int, scaleX: Int, scaleY: Int) {
        maxFPS = Int.MAX_VALUE
        title = sTitle
        title = title
        this.scaleX = scaleX
        this.scaleY = scaleY
        ScreenHeight = height * this.scaleY
        ScreenWidth = width * this.scaleX
        display = View()
        frame = JFrame(title)
        frame.layout = CardLayout()
        frame.add(display, "Hee", 0)
        frame.setSize(ScreenWidth, ScreenHeight)
        frame.setLocationRelativeTo(null)
        frame.isResizable = false
        frame.defaultCloseOperation = 3
        frame.isVisible = true
        display.engine = this
    }

    fun start() {
        display.start()
    }

    fun MouseX(): Int {
        return mouseX
    }

    fun MouseY(): Int {
        return mouseY
    }

    fun ScreenHeight(): Int {
        return ScreenHeight / scaleY
    }

    fun ScreenWidth(): Int {
        return ScreenWidth / scaleX
    }

    fun Clear(color: Color?) {
        FillRect(0, 0, ScreenWidth(), ScreenHeight(), color)
    }

    fun Translate(dx: Int, dy: Int) {
        g!!.translate(dx, dy)
    }

    fun Rotate(r: Int) {
        g!!.rotate(r.toDouble())
    }

    /**
     * **Draws a rectangle**
     *
     * @param x     The x position;
     * @param y     The y position;
     * @param dx    The size in the x axis;
     * @param dy    The size in the y axis;
     * @param colorcolor The color used to draw the rectangle
     * @since 1.0
     */
    fun DrawRect(x: Int, y: Int, dx: Int, dy: Int, color: Color?) {
        g?.let {
            g!!.color = color
            g!!.drawRect(x, y, dx, dy)
        } ?: kotlin.run { println("g is null") }
    }

    fun DrawRect(x: Float, y: Float, dx: Float, dy: Float, color: Color?) {
        g!!.color = color
        g!!.drawRect(x.toInt(), y.toInt(), dx.toInt(), dy.toInt())
    }

    /**
     * **Draws a filled in rectangle**
     *
     * @param x     The x position;
     * @param y     The y position;
     * @param dx    The size in the x axis;
     * @param dy    The size in the y axis;
     * @param color The color used to draw the rectangle
     * @since 1.0
     */
    fun FillRect(x: Int, y: Int, dx: Int, dy: Int, color: Color?) {
        g!!.color = color
        g!!.fillRect(x, y, dx, dy)
    }

    fun FillRect(x: Float, y: Float, dx: Float, dy: Float, color: Color?) {
        g!!.color = color
        g!!.fillRect(x.toInt(), y.toInt(), dx.toInt(), dy.toInt())
    }

    /**
     * **Draws a rounded rectangle**
     *
     * @param x         The x position;
     * @param y         The y position;
     * @param dx        The size in the x axis;
     * @param dy        The size in the y axis;
     * @param arcWidth  The Arc's Width;
     * @param arcHeight The Arc's Height;
     * @param color     The color used to draw the rectangle
     * @since 2.1
     */
    fun DrawRoundedRect(x: Int, y: Int, dx: Int, dy: Int, arcWidth: Int, arcHeight: Int, color: Color?) {
        g!!.color = color
        g!!.drawRoundRect(x, y, dx, dy, arcWidth, arcHeight)
    }

    fun DrawRoundedRect(x: Float, y: Float, dx: Float, dy: Float, arcWidth: Float, arcHeight: Float, color: Color?) {
        g!!.color = color
        g!!.drawRoundRect(
            x.toInt(),
            y.toInt(), dx.toInt(), dy.toInt(), arcWidth.toInt(), arcHeight.toInt()
        )
    }

    /**
     * **Draws a rounded filled in rectangle**
     *
     * @param x         The x position;
     * @param y         The y position;
     * @param dx        The size in the x axis;
     * @param dy        The size in the y axis;
     * @param arcWidth  The Arc's Width;
     * @param arcHeight The Arc's Height;
     * @param color     The color used to draw the rectangle
     * @since 2.1
     */
    fun FillRoundedRect(x: Int, y: Int, dx: Int, dy: Int, arcWidth: Int, arcHeight: Int, color: Color?) {
        g!!.color = color
        g!!.fillRoundRect(x, y, dx, dy, arcWidth, arcHeight)
    }

    fun FillRoundedRect(x: Float, y: Float, dx: Float, dy: Float, arcWidth: Float, arcHeight: Float, color: Color?) {
        g!!.color = color
        g!!.fillRoundRect(
            x.toInt(),
            y.toInt(), dx.toInt(), dy.toInt(), arcWidth.toInt(), arcHeight.toInt()
        )
    }

    /**
     * **Draws a Circle**
     *
     * @param x      The x position;
     * @param y      The y position;
     * @param radius The radius of the Circle
     * @param color  The color used to draw the Circle
     * @since 1.0
     */
    fun DrawCircle(x: Int, y: Int, radius: Int, color: Color?) {
        g!!.color = color
        g!!.drawOval(x, y, radius, radius)
    }

    fun DrawCircle(x: Float, y: Float, radius: Float, color: Color?) {
        g!!.color = color
        g!!.drawOval(x.toInt(), y.toInt(), radius.toInt(), radius.toInt())
    }

    /**
     * **Draws a filled in Circle**
     *
     * @param x      The x position;
     * @param y      The y position;
     * @param radius The radius of the Circle
     * @param color  The color used to draw the Circle
     * @since 1.0
     */
    fun FillCircle(x: Int, y: Int, radius: Int, color: Color?) {
        g!!.color = color
        g!!.fillOval(x, y, radius, radius)
    }

    fun FillCircle(x: Float, y: Float, radius: Float, color: Color?) {
        g!!.color = color
        g!!.fillOval(x.toInt(), y.toInt(), radius.toInt(), radius.toInt())
    }

    /**
     * **Draws a Oval**
     *
     * @param x     The x position;
     * @param y     The y position;
     * @param dx    The size of the oval in the x axis;
     * @param dy    The size of the oval in the y axis;
     * @param color The color used to draw the Circle
     * @since 1.0
     */
    fun DrawOval(x: Int, y: Int, dx: Int, dy: Int, color: Color?) {
        g!!.color = color
        g!!.drawOval(x, y, dx, dy)
    }

    fun DrawOval(x: Float, y: Float, dx: Float, dy: Float, color: Color?) {
        g!!.color = color
        g!!.drawOval(x.toInt(), y.toInt(), dx.toInt(), dy.toInt())
    }

    /**
     * **Draws a filled in Oval**
     *
     * @param x     The x position;
     * @param y     The y position;
     * @param dx    The size of the oval in the x axis;
     * @param dy    The size of the oval in the y axis;
     * @param color The color used to draw the Circle
     * @since 1.0
     */
    fun FillOval(x: Int, y: Int, dx: Int, dy: Int, color: Color?) {
        g!!.color = color
        g!!.fillOval(x, y, dx, dy)
    }

    fun FillOval(x: Float, y: Float, dx: Float, dy: Float, color: Color?) {
        g!!.color = color
        g!!.fillOval(x.toInt(), y.toInt(), dx.toInt(), dy.toInt())
    }

    /**
     * **Draw a string**
     *
     * @param x     The x position
     * @param y     The y position
     * @param color The Color for the String
     * @param s     The String to be Drew
     * @since 1.0
     */
    fun DrawString(x: Int, y: Int, color: Color?, s: Any) {
        g!!.color = color
        g!!.drawString(s.toString(), x, y + 10)
    }

    /**
     * **Draw a string**
     *
     * @param x        The x position
     * @param y        The y position
     * @param color    The Color for the String
     * @param template The template string to be formatted;
     * @param data     The data to fill in the template string;
     * @since 1.0
     */
    fun DrawFormattedString(x: Int, y: Int, color: Color?, template: String?, vararg data: Any?) {
        g!!.color = color
        g!!.drawString(String.format(template!!, *data), x, y + 10)
    }

    /**
     * **Draws an image**
     *
     * @param x   The x position
     * @param y   The y position
     * @param img The buffered image to draw
     * @since 1.0
     */
    fun DrawSprite(x: Int, y: Int, img: Sprite) {
        g!!.drawImage(img.img, x, y, null)
    }

    fun DrawSprite(x: Float, y: Float, img: Sprite) {
        g!!.drawImage(img.img, x.toInt(), y.toInt(), null)
    }

    /**
     * **Gets an part of a image**
     *
     * @param topX    The top x position relative to the image
     * @param topY    The top y position relative to the image
     * @param bottomX The bottom x position relative to the image
     * @param bottomY The bottom y position relative to the image
     * @param img     The buffered image to draw
     * @since 1.0
     */
    fun GetPartialSprite(topX: Int, topY: Int, bottomX: Int, bottomY: Int, img: Sprite): Sprite {
        return Sprite(img.img!!.getSubimage(topX, topY, bottomX, bottomY))
    }

    /**
     * **Draws a pixel****
     *
     * @param x     The x position
     * @param y     The y position
     * @param color The color of the pixel
     * @since 1.0
     ** */
    fun DrawPixel(x: Int, y: Int, color: Color?) {
        g!!.color = color
        g!!.drawLine(x, y, x, y)
    }

    fun DrawPixel(x: Float, y: Float, color: Color?) {
        g!!.color = color
        g!!.drawLine(x.toInt(), y.toInt(), x.toInt(), y.toInt())
    }

    /**
     * **Draw a Line**
     *
     * @param startX The Starting x position
     * @param startY The Starting y position
     * @param endX   The End x position
     * @param endY   The End y position
     * @param color  The Color of the line
     * @since 1.0
     */
    fun DrawLine(startX: Int, startY: Int, endX: Int, endY: Int, color: Color?) {
        g!!.color = color
        g!!.drawLine(startX, startY, endX, endY)
    }

    fun DrawLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color?) {
        g!!.color = color
        g!!.drawLine(startX.toInt(), startY.toInt(), endX.toInt(), endY.toInt())
    }

    /**
     * **Draw a wireframe model**
     *
     * @param modelCoords The Model to Draw
     * @param x           The x position
     * @param y           The y position
     * @param r           The rotation of the model
     * @param s           The scale of the model
     * @param color       The color for the model
     * @since 1.3
     */
    fun DrawWireframeModel(modelCoords: ArrayList<FloatArray>, x: Int, y: Int, r: Float, s: Int, color: Color?) {
        val transformedCoords = ArrayList<FloatArray>()
        val vertices = modelCoords.size
        //Rotate
        for (i in 0 until vertices) {
            val key =
                (modelCoords[i][0] * Math.cos(r.toDouble()) - modelCoords[i][1] * Math.sin(r.toDouble())).toFloat()
            val `val` =
                (modelCoords[i][0] * Math.sin(r.toDouble()) + modelCoords[i][1] * Math.cos(r.toDouble())).toFloat()
            transformedCoords.add(floatArrayOf(key, `val`))
        }
        //Scale
        for (i in 0 until vertices) {
            val key = transformedCoords[i][0] * s
            val `val` = transformedCoords[i][1] * s
            transformedCoords[i] = floatArrayOf(key, `val`)
        }
        //Offset
        for (i in 0 until vertices) {
            val key = transformedCoords[i][0] + x
            val `val` = transformedCoords[i][1] + y
            transformedCoords[i] = floatArrayOf(key, `val`)
        }
        for (i in 0 until vertices + 1) {
            val j = i + 1
            DrawLine(
                transformedCoords[i % vertices][0].toInt(),
                transformedCoords[i % vertices][1].toInt(),
                transformedCoords[j % vertices][0].toInt(),
                transformedCoords[j % vertices][1].toInt(), color
            )
        }
    }

    /**
     * Get the pixel color in the x,y coords off the given image
     *
     * @param img The Sprite;
     * @param x   The x coord of the pixel in the image
     * @param y   The y coord of the pixel in the image
     * @return The Color of The given pixel
     * @since 2.1
     */
    fun GetPixel(img: Sprite, x: Int, y: Int): Color {
        val rgb = img.img!!.getRGB(x, y)
        val red = rgb shr 16 and 0xFF
        val green = rgb shr 8 and 0xFF
        val blue = rgb and 0xFF
        return Color(red, green, blue)
    }

    fun GetTintedSprite(sprite: Sprite, color: Color): Sprite {
        val tintedSprite = BufferedImage(sprite.img!!.width, sprite.img!!.height, BufferedImage.TRANSLUCENT)
        val graphics = tintedSprite.createGraphics()
        graphics.drawImage(sprite.img, 0, 0, null)
        graphics.dispose()
        val r = map(color.red.toFloat(), 0f, 255f, 0f, 1f)
        val g = map(color.green.toFloat(), 0f, 255f, 0f, 1f)
        val b = map(color.blue.toFloat(), 0f, 255f, 0f, 1f)
        val a = map(color.alpha.toFloat(), 0f, 255f, 0f, 1f)
        for (i in 0 until tintedSprite.width) {
            for (j in 0 until tintedSprite.height) {
                var ax = tintedSprite.colorModel.getAlpha(tintedSprite.raster.getDataElements(i, j, null))
                var rx = tintedSprite.colorModel.getRed(tintedSprite.raster.getDataElements(i, j, null))
                var gx = tintedSprite.colorModel.getGreen(tintedSprite.raster.getDataElements(i, j, null))
                var bx = tintedSprite.colorModel.getBlue(tintedSprite.raster.getDataElements(i, j, null))
                rx *= r.toInt()
                gx *= g.toInt()
                bx *= b.toInt()
                ax *= a.toInt()
                tintedSprite.setRGB(i, j, ax shl 24 or (rx shl 16) or (gx shl 8) or bx)
            }
        }
        return Sprite(tintedSprite)
    }

    fun GetTintedPartialSprite(topX: Int, topY: Int, bottomX: Int, bottomY: Int, img: Sprite, color: Color): Sprite {
        return GetTintedSprite(Sprite(img.img!!.getSubimage(topX, topY, bottomX, bottomY)), color)
    }

    class View : Canvas(), Runnable {
        private var scrollResetTimer: Long = 0
        private var thread: Thread? = null
        var engine: PixelGameEngine? = null
        @Synchronized
        fun start() {
            isRunning = true
            thread = Thread(this, "Render")
            thread!!.start()
        }

        @Synchronized
        fun stop() {
            isRunning = false
            try {
                thread!!.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        private fun render(delta: Float) {
            val bs = bufferStrategy
            if (bs == null) {
                this.createBufferStrategy(4)
                return
            }
            if (System.currentTimeMillis() - scrollResetTimer > 100) {
                engine!!.nScrollDir = 0
            }
            g = bs.drawGraphics as Graphics2D
            g!!.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED)
            g!!.scale(engine!!.scaleX.toDouble(), engine!!.scaleY.toDouble())
            g!!.font = Font("Consolas", 1, 8)
            if (!engine!!.OnUserUpdate(delta)) {
                engine!!.OnUserDestroy()
                stop()
            }
            g!!.dispose()
            bs.show()
        }

        override fun run() {
            var lastTime = System.nanoTime()
            var currentTime = System.currentTimeMillis()
            val ns = 1000000000.0 / 60f
            var delta = 0.0
            var frames = 0
            var renderLastTime = System.nanoTime()
            val amtOfRenders = maxFPS.toDouble() //MAX FPS
            val renderNs = 1000000000 / amtOfRenders
            var renderDelta = 0.0


            var bs = bufferStrategy
            if (bs == null) {
                this.createBufferStrategy(4)
            }
            bs = bufferStrategy
            g = bs.drawGraphics as Graphics2D
            g!!.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED)
            g!!.scale(engine!!.scaleX.toDouble(), engine!!.scaleY.toDouble())
            g!!.font = Font("Consolas", 1, 8)

            if (!engine!!.OnUserCreate()) {
                stop()
            }
            while (isRunning) {
                var now = System.nanoTime()
                delta += (now - lastTime) / ns
                lastTime = now
                while (delta >= 1) {
                    delta--
                }
                now = System.nanoTime()
                renderDelta += (now - renderLastTime) / renderNs
                renderLastTime = now
                while (isRunning && renderDelta >= 1) {
                    render(delta.toFloat())
                    frames++
                    renderDelta--
                    if (System.currentTimeMillis() - currentTime > 1000) {
                        currentTime += 1000
                        fps = frames
                        engine!!.frame.title = title + " | FPS :" + frames
                        frames = 0
                    }
                }
            }
            stop()
        }

        init {
            isFocusable = true
            addKeyListener(object : KeyAdapter() {
                override fun keyPressed(keyEvent: KeyEvent) {
                    engine!!.b_keys[keyEvent.keyCode] = true
                }

                override fun keyReleased(keyEvent: KeyEvent) {
                    engine!!.b_keys[keyEvent.keyCode] = false
                }
            })
            addMouseListener(object : MouseAdapter() {
                override fun mousePressed(mouseEvent: MouseEvent) {
                    engine!!.b_mouse[mouseEvent.button] = true
                }

                override fun mouseReleased(mouseEvent: MouseEvent) {
                    engine!!.b_mouse[mouseEvent.button] = false
                }
            })
            addMouseMotionListener(object : MouseAdapter() {
                override fun mouseMoved(mouseEvent: MouseEvent) {
                    engine!!.mouseX = mouseEvent.x / engine!!.scaleX
                    engine!!.mouseY = mouseEvent.y / engine!!.scaleY
                }

                override fun mouseDragged(mouseEvent: MouseEvent) {
                    engine!!.mouseX = mouseEvent.x / engine!!.scaleX
                    engine!!.mouseY = mouseEvent.y / engine!!.scaleY
                }
            })
            addMouseWheelListener { mouseWheelEvent ->
                engine!!.nScrollDir = mouseWheelEvent.wheelRotation
                scrollResetTimer = System.currentTimeMillis()
            }
        }
    }

    companion object {
        private var title: String = ""
        private var isRunning = false
        protected var fps = 0
        var g: Graphics2D? = null
        protected var maxFPS: Int = 60
        private fun map(value: Float, istart: Float, istop: Float, ostart: Float, ostop: Float): Float {
            return ostart + (ostop - ostart) * ((value - istart) / (istop - istart))
        }
    }
}