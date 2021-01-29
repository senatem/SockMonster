package com.pungo.modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.inputProcessor.InputHandler
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.PixmapGenerator
import com.pungo.modules.visuals.fromFont.BlockText
import com.pungo.modules.visuals.fromFont.TextAlignment
import com.pungo.modules.visuals.textureHandling.SingleTexture

class TypingBox(id: String, initialText: String = "", block: LcsRect = GetLcsRect.ofFullScreen(), private var charLimit: Int = 0, private var numOnly: Boolean = false) : UiElement(id) {

    var selected = false
    private var textString = ""
    var textChangeFun = {}


    private var bg: OmniVisual = SingleTexture(PixmapGenerator.singleColour()).also {
        it.recolour(Color.WHITE)
        it.reBlock(GetLcsRect.byParameters(block.width, block.height))
    }
    private var invalid = bg.copy().also {
        it.recolour(Color.DARK_GRAY)
    }
    private var invalidEntry = 0

    private var bt = BlockText(initialText, 16, Color.BLACK, align = TextAlignment.LEFT, padding = GetLcs.byPixel(5f), fontPath = "fonts/PTMono-Regular.ttf")


    override fun touchHandler(mayTouch: Boolean): Boolean {
        val contains = district.block.contains(GetLcs.ofX(), GetLcs.ofY())
        if (Gdx.input.justTouched()) { //pressed somewhere else
            selected = false
        }
        if (contains) {
            if (Gdx.input.isButtonJustPressed(0)) {
                selected = true
            }
        }
        return contains
    }

    override fun update() {
        if (selected) {
            val oldString = textString
            InputHandler.getTypeCache(true).forEach {
                if (it == '\b') {
                    if (textString.isNotEmpty()) {
                        textString = textString.dropLast(1)
                    } else {
                        invalidEntry = 3
                    }
                } else {
                    try {
                        if (textString.length >= charLimit) throw Exception("size")
                        it.toString().toInt()
                        textString += it
                    } catch (e: NumberFormatException) {
                        if (!numOnly) {
                            textString += it
                        } else {
                            invalidEntry = 3
                        }
                    } catch (e: Exception) {
                        invalidEntry = 3
                    }
                }
            }
            if (oldString != textString) {
                textChange()
            }
        }
    }

    private fun textChange() {
        bt.text = textString
        textChangeFun()
    }

    /*
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width,block.height,x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w,h,block.cX,block.cY)
    }

     */

    override fun draw(batch: SpriteBatch, alpha: Float) {
        bg.draw(batch, alpha)
        bt.draw(batch, alpha)
        if (invalidEntry > 0) {
            invalid.draw(batch, alpha)
            invalidEntry -= 1
        }
    }

    override fun dispose() {
        bg.dispose()
        bt.dispose()
        invalid.dispose()
    }

}