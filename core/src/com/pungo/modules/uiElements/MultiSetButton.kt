package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch

/** This is a collection of set buttons that, in addition to buttons' function, switches the displayed button with every click
 * I really really hope this works cos I don't want to figure out a different way to do this
 */
class MultiSetButton(id: String) : UiElement(id) {
    var buttonsList = mutableListOf<SetButton>()
    private var activeButton = 0


    fun addButton(b: SetButton) {
        if (buttonsList.none { it.id == b.id }) {
            district.addFullPlot(b.id).element = b
            val bottle = b.clicked
            b.clicked = { bottle();rollActiveButton() }
            buttonsList.add(b)
            if (buttonsList.size > 1) {
                district.findPlot(b.id).visible = false
            }
        } else {
            throw Exception("ERROR id clash in multi button $id ")
        }
    }

    fun removeButton(id: String) {
        buttonsList = buttonsList.filter { it.id != id }.toMutableList()
    }

    fun changeActiveButton(n: Int) {
        district.findPlot(buttonsList[activeButton].id).visible = false
        activeButton = n
        district.findPlot(buttonsList[activeButton].id).visible = true
    }

    private fun rollActiveButton() {
        district.findPlot(buttonsList[activeButton].id).visible = false
        activeButton += 1
        if (activeButton == buttonsList.size) activeButton = 0
        district.findPlot(buttonsList[activeButton].id).visible = true
    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        if (visible) {
            if (buttonsList.none()) {
                return false
            }
            return buttonsList[activeButton].touchHandler(mayTouch)
        } else {
            return false
        }

    }

    override fun update() {
        buttonsList.forEach {
            it.update()
        }
    }


    /*

    override fun relocate(x: LcsVariable, y: LcsVariable) {
        buttonsList.forEach {
            it.relocate(x,y)
        }
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        buttonsList.forEach {
            it.resize(w,h)
        }
    }

     */

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (visible) {
            district.draw(batch, alpha)
        }
    }

    override fun dispose() {
        buttonsList.forEach { it.dispose() }
    }


}