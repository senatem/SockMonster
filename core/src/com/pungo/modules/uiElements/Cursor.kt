package com.pungo.modules.uiElements

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.visuals.OmniVisual

class Cursor(private var image: OmniVisual) : UiElement("Cursor") {
    //override lateinit var block: LcsRect


    private var lastPressTime = 0L
    private var active = true

    init {
        district.block = image.block
    }


    override fun touchHandler(mayTouch: Boolean): Boolean {
        when (Gdx.app.type) {
            Application.ApplicationType.Android, Application.ApplicationType.iOS -> {
                if (Gdx.input.justTouched()) {
                    val nowTime = System.currentTimeMillis()
                    if (nowTime - lastPressTime < 500) {
                        active = active.not()
                    }
                    lastPressTime = System.currentTimeMillis()
                }
            }
            Application.ApplicationType.Desktop -> {
                if (Gdx.input.isButtonJustPressed(0)) {
                    active = active.not()
                }
            }
            else -> {
                throw Exception("I DON'T KNOW WHAT IM WORKING ON\nI MEAN WHAT IS ${Gdx.app.type}")
            }
        }
        return false
    }

    fun recolour(c: Color) {
        image.recolour(c)
    }


    override fun update() {
        touchHandler()
    }

    /*
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width, block.height, x, y)
        image.relocate(x, y)
    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w, h, block.cX, block.cY)
        image.resize(w, h)
    }

     */

    override fun draw(batch: SpriteBatch, alpha: Float) {
        if (active) {
            relocate(GetLcs.ofX(), GetLcs.ofY())
            image.reBlock(getBlock())
            image.draw(batch, alpha)
        }

    }

    override fun dispose() {
        image.dispose()
    }

}