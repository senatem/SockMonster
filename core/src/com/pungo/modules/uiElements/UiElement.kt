package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.lcsModule.LcsVariable
import com.pungo.modules.uiPlots.District

abstract class UiElement(var id: String) {
    init {
        if (id.contains("&")) throw Exception("ID: $id contains illegal character")
    }

    protected val district = District(id)

    var visible = true //adjusts visibility

    var mouseHovering = false //true if mouse is hovering on it in a meaningful way

    abstract fun touchHandler(mayTouch: Boolean = true): Boolean

    abstract fun update()

    abstract fun draw(batch: SpriteBatch, alpha: Float = 1f)

    abstract fun dispose()

    open fun getValue(): Int {
        return 0
    }

    /** this function returns true if it is hovering
     * it does not switch hovering to true, because mouse being on something does not necessarily mean it is hovering
     */
    fun hovering(): Boolean {
        return district.block.contains(GetLcs.ofX(), GetLcs.ofY())
    }

    fun relocate(x: LcsVariable, y: LcsVariable) {
        district.block = district.block.relocateTo(x, y)
    }

    fun resize(w: LcsVariable, h: LcsVariable) {
        district.block = district.block.resizeTo(w, h)
    }

    fun reBlock(b: LcsRect) {
        district.block = b
    }

    fun getBlock(): LcsRect {
        return district.block.copy()
    }

}