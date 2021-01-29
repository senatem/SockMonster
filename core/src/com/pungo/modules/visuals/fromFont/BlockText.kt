package com.pungo.modules.visuals.fromFont

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.subTexture.ScalingType
import com.pungo.modules.visuals.subTexture.VisualSizeData
import com.pungo.modules.lcsModule.LcsVariable as lv

/** Creates a textbox with the set text
 * align: -1 left, 0 right, 1 centre
 * height currently is not applied as a vertical limit, it may be in the future.
 * padding: an extra distance to top and bottom by pixel
 * keepWords: true means, if limits are exceeded, excessive text will be displayed, if not it is cut from there
 */
class BlockText(text: String, size: Int, colour: Color, var fontPath: String, block: LcsRect = GetLcsRect.ofCentreSquare(), var align: TextAlignment = TextAlignment.LEFT, var padding: lv = GetLcs.byLcs(0f), private var keepWords: Boolean = false, scalingType: ScalingType = ScalingType.FIT_ELEMENT, widthScaleFactor: Float = 1f, heightScaleFactor: Float = 1f) : OmniVisual() {
    var text: String = text
        set(value) {
            field = value
            gl.text = value
        }


    private var initSize = size
    private var gl = BetterGlyphLayout(fontPath, size)
    private var visualSizeData = VisualSizeData(block, scalingType = scalingType, widthScaleFactor = widthScaleFactor, heightScaleFactor = heightScaleFactor)
    var colour = colour
        private set


    init {
        visualSizeData = visualSizeData.copy(originalRect = this.block.copy())
        visualSizeData.updateImageBlock(block)
        gl.text = text
        updateFont()
    }

    override fun setScalingType(scalingType: ScalingType?, widthScaleFactor: Float?, heightScaleFactor: Float?) {
        visualSizeData = VisualSizeData(this.block.copy(),
                scalingType = scalingType ?: visualSizeData.scalingType,
                widthScaleFactor = widthScaleFactor ?: visualSizeData.widthScaleFactor,
                heightScaleFactor = heightScaleFactor ?: visualSizeData.heightScaleFactor)
    }


    override fun setFlip(x: Boolean?, y: Boolean?, vFlipOrigin: Float?, hFlipOrigin: Float?) {
        //TODO flipping for text
    }


    override fun draw(batch: SpriteBatch, alpha: Float) {
        visualSizeData.updateImageBlock(block)
        val imageBlock = visualSizeData.imageBlock
        if (gl.width != visualSizeData.imageBlock.width.asPixel()) {
            updateFont()
        }
        val y = when (align.ordinal / 3) {
            0 -> imageBlock.hEnd.asPixel()
            1 -> imageBlock.cY.asPixel() + gl.height / 2
            else -> imageBlock.hStart.asPixel() + gl.height - (gl.font?.descent ?: 0f)
        }
        gl.font?.draw(batch, gl, imageBlock.wStart.asPixel(), y)
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {}
    override fun recolour(c: Color) {
        colour = c
        gl.setTextProperties(colour = c)
    }

    private fun updateFont() {
        if ((initSize != gl.punto) || (fontPath != gl.path)) {
            gl.setFontProperties(initSize, fontPath)
        }
        gl.setTextProperties(colour, visualSizeData.imageBlock.width.asPixel(), align, wrap = true)
    }

    override fun copy(): OmniVisual {
        return BlockText(text, initSize, colour, fontPath, block, align, padding, keepWords, visualSizeData.scalingType, visualSizeData.widthScaleFactor, visualSizeData.heightScaleFactor)
    }

    fun textChanged(t: String): BlockText {
        return BlockText(t, initSize, colour, fontPath, block, align, padding, keepWords, visualSizeData.scalingType, visualSizeData.widthScaleFactor, visualSizeData.heightScaleFactor)
    }

    /** Returns a block text with different size, but same otherwise
     */
    fun sizeChanged(s: Int, copyIfSame: Boolean = false): BlockText {
        return if (copyIfSame) {
            BlockText(text, s, colour, fontPath, block, align, padding, keepWords, visualSizeData.scalingType, visualSizeData.widthScaleFactor, visualSizeData.heightScaleFactor)
        } else {
            if (s != initSize) {
                BlockText(text, s, colour, fontPath, block, align, padding, keepWords, visualSizeData.scalingType, visualSizeData.widthScaleFactor, visualSizeData.heightScaleFactor)
            } else {
                this
            }
        }
    }

    override fun dispose() {
    }

}