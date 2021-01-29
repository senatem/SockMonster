package com.pungo.modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.visuals.subTexture.ScalingType
import kotlin.math.ceil

/** Used to create custom pixmaps which are returned as CustomPixmap visual elements
 *
 */
object PixmapGenerator {
    fun singleColour(): Pixmap {
        Pixmap(11, 11, Pixmap.Format.RGBA8888).also {
            it.setColor(1f, 1f, 1f, 1f)
            it.fill()
            return it
        }
    }


    /** Creates a grid with col and row
     */
    fun grid(row: Int, col: Int, b: LcsRect = GetLcsRect.ofFullScreen()): Pixmap {
        val w = ceil(b.width.asPixel()).toInt()
        val h = ceil(b.height.asPixel()).toInt()
        Pixmap(w, h, Pixmap.Format.RGBA8888).also {
            it.setColor(Color.LIGHT_GRAY) //sets colour permanently
            it.drawRectangle(0, 0, w, h)
            for (i in (1 until col)) {
                val xVal = w * (i.toFloat() / col)
                it.fillRectangle(ceil(xVal).toInt() - 1, 0, 3, h)
            }
            for (i in (1 until row)) {
                val yVal = h * (i.toFloat() / row)
                it.fillRectangle(0, ceil(yVal).toInt() - 1, w, 3)
            }
            return it
            /*
            return CustomPixmap(it, c, scalingType, scaleFactor).also { it2->
                it2.reBlock(b)
            }

             */
        }
    }

    fun circle(b: LcsRect = GetLcsRect.ofCentreSquare(), c: Color = Color.WHITE, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f): Pixmap {
        Pixmap(101, 101, Pixmap.Format.RGBA8888).also {
            it.setColor(1f, 1f, 1f, 1f)
            it.fillCircle(50, 50, 50)
            return it
            /*
            return CustomPixmap(it, c, scalingType, scaleFactor).also { it2->
                it2.reBlock(b)
            }
             */
        }
    }
}