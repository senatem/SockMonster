package com.pungo.modules.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.visuals.subTexture.ScalingType

class TwoVisuals(var front: OmniVisual, private var back: OmniVisual, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f) : OmniVisual() {
    override var block = GetLcsRect.ofZero()
        set(value) {
            field = value
            back.reBlock(value)
            front.reBlock(value)
        }


    override fun draw(batch: SpriteBatch, alpha: Float) {

        back.draw(batch, alpha)
        front.draw(batch, alpha)
    }

    override fun changeActiveSprite(ns: Int) {}

    fun swapFrontSprite() {
        back = front.also {
            front = back
        }
    }

    override fun update() {
        back.update()
        front.update()
        back.reBlock(block)
        front.reBlock(block)
    }

    override fun recolour(c: Color) {
        back.recolour(c)
        front.recolour(c)
    }

    override fun copy(): OmniVisual {
        return TwoVisuals(front.copy(), back.copy())
    }

    override fun dispose() {
        front.dispose()
        back.dispose()
    }

    /*
    override fun updateVisual() {
        front.reBlock(block)
        back.reBlock(block)
    }

     */

    override fun setFlip(x: Boolean?, y: Boolean?, vFlipOrigin: Float?, hFlipOrigin: Float?) {
        front.setFlip(x, y, vFlipOrigin, hFlipOrigin)
        back.setFlip(x, y, vFlipOrigin, hFlipOrigin)
    }
}