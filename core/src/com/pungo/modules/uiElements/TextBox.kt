package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.visuals.fromFont.BetterGlyphLayout
import com.pungo.modules.visuals.fromFont.BlockText
import com.pungo.modules.visuals.fromFont.FontGenerator
import com.pungo.modules.visuals.fromFont.TextAlignment

class TextBox : UiElement {
    var blockText: BlockText
    var sizeUp: Int = 112
    var sizeDown: Int = 8
    var forcedSize: Int? = null

    constructor(id: String, text: BlockText, sizeUp: Int = 112, sizeDown: Int = 8) : super(id) {
        this.sizeUp = sizeUp
        this.sizeDown = sizeDown
        this.blockText = text
    }

    constructor(id: String, text: String, path: String, sizeUp: Int, sizeDown: Int = 8, colour: Color = Color.WHITE, alignment: TextAlignment = TextAlignment.CENTRE) : super(id) {
        this.sizeUp = sizeUp
        this.sizeDown = sizeDown
        this.blockText = BlockText(text, sizeUp, colour, path, align = alignment)
    }

    fun fittingPuntos(): List<Int> {
        return if (sizeUp != sizeDown) {
            val q = FontGenerator.getFontsBetween(sizeDown..sizeUp, blockText.fontPath).map { BetterGlyphLayout(it, blockText.text, targetWidth = district.block.width.asPixel()) }.filter { it.height - it.font!!.descent < district.block.height.asPixel() }.map { it.punto }
            if (q.isNotEmpty()) {
                q
            } else {
                listOf(this.sizeDown)
            }
        } else {
            listOf(sizeUp)
        }

    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        return hovering() && mayTouch
    }

    override fun update() {
        blockText.update()
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (visible) {
            blockText.reBlock(getBlock())
            blockText = blockText.sizeChanged(forcedSize ?: fittingPuntos().last())
            blockText.draw(batch, alpha)
        }
    }

    override fun dispose() {

    }

    fun changeText(t: String) {
        blockText = blockText.textChanged(t)
    }

    fun recolour(c: Color) {
        blockText.recolour(c)
    }

    fun getTextCopy(t: String? = null, c: Color? = null): BlockText {
        val colour = c ?: blockText.colour
        return if (t == null) {
            (blockText.copy() as BlockText).also {
                it.recolour(colour)
            }
        } else {
            blockText.textChanged(t).also {
                it.recolour(colour)
            }
        }
    }

}