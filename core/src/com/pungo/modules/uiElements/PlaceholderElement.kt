package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class PlaceholderElement(id: String) : UiElement(id) {
    // override var block = GetLcsRect.ofZero()


    override fun touchHandler(mayTouch: Boolean): Boolean {
        return false
    }

    override fun update() {}
    // override fun relocate(x: LcsVariable, y: LcsVariable) {}
    // override fun resize(w: LcsVariable, h: LcsVariable) {}

    override fun draw(batch: SpriteBatch, alpha: Float) {}
    override fun dispose() {}
}