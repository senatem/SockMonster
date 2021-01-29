package com.pungo.modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.PixmapGenerator
import com.pungo.modules.visuals.textureHandling.SingleTexture

/** Set button executes function clicked when clicked, but is similar to pinup visual otherwise
 * it needs to be loaded with basic size parameters, in the form of block
 * and it needs on and off visuals
 */
class SetButton(id: String) : UiElement(id) {
    init {
        district.addFullPlot("down").element?.visible = false
        district.addFullPlot("up")
        district.addFullPlot("inactive", z = 10).element?.visible = false
    }

    private var pressing = false
        private set(value) {
            field = value
            district.setVisible("up", pressing.not())
            district.setVisible("down", pressing)


        }

    var clicked = { println("$id clicked") }
    var deactivate = false
        set(value) {
            field = value
            district.findPlot("inactive").element?.visible = value
        }

    constructor(id: String, block: LcsRect) : this(id) {
        district.block = block
        district.findPlot("up").element = PinupImage("up", SingleTexture(PixmapGenerator.singleColour()).also {
            it.recolour(Color.WHITE)
        })
        district.findPlot("down").element = PinupImage("down", SingleTexture(PixmapGenerator.singleColour()).also {
            it.recolour(Color.DARK_GRAY)
        })
    }

    constructor(id: String, onElement: UiElement, offElement: UiElement, block: LcsRect? = null) : this(id) {
        if (block != null) {
            district.block = block
        } else {
            district.block = onElement.getBlock()
        }
        district.findPlot("up").element = onElement
        district.findPlot("down").element = offElement
        pressing = false //this sets visibility
    }

    constructor(id: String, onVisual: OmniVisual, offVisual: OmniVisual, block: LcsRect? = null) : this(id, PinupImage("up", onVisual), PinupImage("down", offVisual), block)

    constructor(id: String, onVisual: OmniVisual, offColour: Color = Color(0.5f, 0.5f, 0.5f, 1f), block: LcsRect? = null) : this(id, onVisual, onVisual.copy().also {
        it.recolour(offColour)
    }, block)

    fun setInactiveVisual(omniVisual: OmniVisual) {
        setInactiveElement(PinupImage("inactive", omniVisual))
    }

    private fun setInactiveElement(uiElement: UiElement) {
        district.findPlot("inactive").element = uiElement
    }


    /** Handles touching
     * hierarchy is handled by the callers in layout
     */
    override fun touchHandler(mayTouch: Boolean): Boolean {
        if (mayTouch) {
            val containing = hovering()
            if (containing && deactivate) return true
            if (pressing) {
                pressing = if (Gdx.input.isTouched) {
                    containing
                } else {
                    clicked()
                    false
                }
            } else {
                if (Gdx.input.justTouched()) {
                    pressing = containing
                }
            }
            return containing
        } else {
            pressing = false
            return false
        }

    }


    /** Runs on every update
     */
    override fun update() {}

    /** Called on every draw
     */
    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (visible) {
            district.draw(batch, alpha)
        }
    }

    override fun dispose() {
        district.dispose()
    }

    override fun getValue(): Int {
        return if (pressing) 1 else 0
    }


}