package com.pungo.game

import com.pungo.modules.inputProcessor.BasicListener
import com.pungo.modules.scenes.LayerManager

class SockMonsterEventListener : BasicListener() {
    override fun keyUp(keycode: Int): Boolean {
        LayerManager.layers.last().keyUp(keycode)
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        LayerManager.layers.last().keyDown(keycode)
        return true
    }
}