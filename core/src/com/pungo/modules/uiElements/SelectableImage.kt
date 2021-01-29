package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.PixmapGenerator
import com.pungo.modules.visuals.textureHandling.SingleTexture

class SelectableImage(id: String, selectColor: Color, image: OmniVisual, block: LcsRect = GetLcsRect.ofFullScreen()) : UiElement(id) {
    init {
        district.block = block
    }

    /*
    override var block: LcsRect = block
        set(value) {
            field = value
            image.resize(value.width*0.8f,value.height*0.8f)
            image.relocate(value.cX,value.cY)
            background.reBlock(value)
        }

     */
    private var background = SingleTexture(PixmapGenerator.singleColour()).also {
        it.reBlock(block)
        it.recolour(selectColor)
    }
    var image = SetButton("sb", image, image.copy().apply { recolour(Color.DARK_GRAY) }).also {
        it.clicked = {
            selected = selected.not()
        }
    }
    var selected = false
        set(value) {
            if (value) {
                selectFunc()
            } else {
                unselectFunc()
            }
            field = value
        }
    var selectFunc = {}
    var unselectFunc = {}


    override fun touchHandler(mayTouch: Boolean): Boolean {
        return image.touchHandler(mayTouch)
    }

    override fun update() {
        background.update()
        image.update()
    }

    /*
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = block.relocateTo(x,y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = block.resizeTo(w,h)
    }

     */

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (selected) background.draw(batch, alpha)
        //background.draw(batch,alpha)
        image.draw(batch, alpha)
    }

    override fun dispose() {
        image.dispose()
        background.dispose()
    }
}