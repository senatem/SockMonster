package com.pungo.modules.uiElements.camera

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.uiElements.UiElement

class Camera(id: String, block: LcsRect, subjectList: List<UiElement>, zoomRect: ZoomRect) : UiElement(id) {
    var subjects = subjectList.toMutableList()
    var zoomRect = zoomRect
        set(value) {
            field = value
            subjects.forEach {
                it.resize(district.block.width / value.width, district.block.height / value.height)
                it.relocate(district.block.cX + it.getBlock().width * (0.5f - value.cX), district.block.cY + it.getBlock().height * (0.5f - value.cY))
            }
        }


    /*
    override var block: LcsRect = block
        set(value) {
            field = value
            subjects.forEach {subject->
                subject.resize(value.width/zoomRect.width,value.height/zoomRect.height)
                subject.relocate(value.cX + subject.getBlock().width*(0.5f - zoomRect.cX),value.cY + subject.getBlock().height*(0.5f - zoomRect.cY))
            }

        }

     */


    override fun touchHandler(mayTouch: Boolean): Boolean {
        subjects.reversed().forEach {
            if (it.touchHandler(mayTouch)) return true
        }
        return false
    }

    override fun update() {
        subjects.forEach {
            it.update()
        }
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
        subjects.forEach {
            it.draw(batch, alpha)
        }
    }

    override fun dispose() {
        subjects.forEach {
            it.dispose()
        }
    }
}