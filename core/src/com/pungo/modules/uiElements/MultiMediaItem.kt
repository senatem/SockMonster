package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.FastGeometry
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.visuals.OmniVisual

/** Also called the Damla's item, this item contains a multitude of elements and display patterns can be altered by various commands
 *
 */
class MultiMediaItem(id: String) : UiElement(id) {
    private var faceOther = false

    /** This guy adds an element with desired location and size qualities
     * left boolean denotes that this element's rectangle is the left flipped version
     */
    fun addElement(element: UiElement, rectangle: Rectangle = FastGeometry.unitSquare()) {
        district.addFullPlot(element.id, rectangle).also {
            it.element = element

        }
    }

    fun findElement(id: String): UiElement? {
        return district.plots.filter {
            it.element?.id == id
        }.first().element
    }

    /** This guy is just to make things easier for the above function
     * also it sets the flip origins automatically
     */
    fun addElement(id: String, visual: OmniVisual, rectangle: Rectangle = FastGeometry.unitSquare()) {
        val vfo = (1f - (rectangle.left * 0.5f + rectangle.right * 0.5f) - rectangle.left) / rectangle.width
        val hfo = (1f - (rectangle.top * 0.5f + rectangle.bottom * 0.5f) - rectangle.bottom) / rectangle.height
        visual.setFlip(vFlipOrigin = vfo, hFlipOrigin = hfo)
        addElement(PinupImage(id, visual), rectangle)
    }

    fun setVisibility(id: String, visible: Boolean) {
        district.findPlot(id).element!!.visible = visible
    }

    /**
     *
     */
    fun invisibleExcept(id: String = "") {
        district.makeAllInvisible()
        if (id != "") {
            district.findPlot(id).element!!.visible = true
        }
    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        return false
    }

    override fun update() {
        district.update()

    }

    fun turnAllRight() {
        faceOther = true
        district.plots.forEach {

            val e = it.element
            if (e is PinupImage) {
                e.image.setFlip(x = true, y = false)
            }
        }
    }

    fun turnAllLeft() {
        faceOther = false
        district.plots.forEach {
            val e = it.element
            if (e is PinupImage) {
                e.image.setFlip(x = false, y = false)
            }
        }
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        district.draw(batch, alpha, faceOther)
    }

    override fun dispose() {}
}