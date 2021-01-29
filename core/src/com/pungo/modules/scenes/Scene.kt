package com.pungo.modules.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.uiPlots.District

/** Scenes are used to arrange and use districts
 *
 */
open class Scene(val id: String, var zOrder: Float, var keepRatio: Boolean = false) {
    var visible = true
    var mainDistrict = District("${id}_district")
        private set

    open fun entering() {

    }

    open fun draw(batch: SpriteBatch) {
        if (visible) {
            mainDistrict.draw(batch)
        }

    }

    open fun update() {
        if (visible) {
            if (keepRatio) {
                mainDistrict.block = GetLcsRect.ofFittingRatioToScreen()
            }
            mainDistrict.update()
            mainDistrict.plots.forEach {
                it.element?.touchHandler(true)
            }
        }
    }


    open fun dispose() {
        mainDistrict.dispose()
    }

    open fun mouseMoved(screenX: Int, screenY: Int) {

    }

    open fun keyTyped(character: Char) {

    }

    open fun keyUp(keycode: Int) {

    }

    open fun keyDown(keycode: Int) {

    }

    open fun touchUp(screenX: Int, screenY: Int) {

    }

    open fun touchDragged(screenX: Int, screenY: Int) {

    }

    open fun touchDown(screenX: Int, screenY: Int) {

    }
}