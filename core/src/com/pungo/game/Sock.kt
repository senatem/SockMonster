package com.pungo.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.game.scenes.SockType
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsVariable
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture
import kotlin.math.cos
import kotlin.math.sin

class Sock(val id: String, path: FileHandle, sockType: SockType, clickFunction: ()-> Unit={}) {
    val w: LcsVariable = sockType.getWidth()
    val h: LcsVariable= sockType.getHeight()
    val sb = SetButton("sb", SingleTexture(path), SingleTexture(path), GetLcsRect.byParameters(w,h)).also {
        it.clicked = clickFunction
    }

    fun draw(batch: SpriteBatch){
        sb.draw(batch)
    }

    fun relocate(x: LcsVariable,y: LcsVariable){
        sb.relocate(x,y)
    }

    fun update(){
        sb.update()
    }

    fun touchHandler(){
        sb.touchHandler(true)
    }

    fun relocate(th: Float, radius: LcsVariable, drumCentre: Pair<LcsVariable,LcsVariable>){
        val dx = radius* sin(th)
        val dy = radius* cos(th)
        val cx = drumCentre.first + dx
        val cy = drumCentre.second + dy
        relocate(cx,cy)
    }

    fun modifyClickFunction(clickFunction: () -> Unit){
        sb.clicked = clickFunction
    }
}