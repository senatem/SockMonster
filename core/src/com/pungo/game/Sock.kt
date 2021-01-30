package com.pungo.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.game.scenes.SockType
import com.pungo.modules.basic.geometry.Angle
import com.pungo.modules.basic.geometry.Point
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsVariable
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.visuals.textureHandling.SingleTexture
import kotlin.math.cos
import kotlin.math.sin

class Sock(val id: String, path: FileHandle, val sockType: SockType, var clickFunction: ()-> Unit={}) {
    val w: LcsVariable = sockType.getWidth()
    val h: LcsVariable= sockType.getHeight()
    var cX = GetLcs.ofZero()
    var cY = GetLcs.ofZero()
    var theta = Angle(0f)
    var speed = 0.005f
    var held = false
    val image = PinupImage("sb",SingleTexture(path)).also {
        it.resize(w,h)
    }

    fun draw(batch: SpriteBatch){
        (image.image as SingleTexture).subTexture.setOriginCenter()
        (image.image as SingleTexture).subTexture.rotation = -(theta).deg
        image.draw(batch)
    }

    private fun relocate(x: LcsVariable,y: LcsVariable){
        image.relocate(x,y)
    }

    fun update(){
        image.update()
    }

    fun touchHandler(mayTouch: Boolean=true): Boolean{
        return if(mayTouch&&relativeClick()){
            if(Gdx.input.justTouched()){
                held = true
            }

            if((!Gdx.input.isTouched)&&held){
                held = false
                clickFunction()
                println("hey")
            }
            true
        }else{
            held=false
            false
        }
    }

    fun relocate(radius: LcsVariable, drumCentre: Pair<LcsVariable,LcsVariable>){
        val dx = radius* sin(theta.radian)
        val dy = radius* cos(theta.radian)
        cX = drumCentre.first + dx
        cY = drumCentre.second + dy
        relocate(cX,cY)
    }

    fun modifyClickFunction(clickFunction: () -> Unit){
        this.clickFunction = clickFunction
    }

    fun relativeClick(x: LcsVariable = GetLcs.ofX(),y: LcsVariable = GetLcs.ofY()): Boolean {
        val rect = GetLcsRect.byParameters(w,h,cX,cY)
        val rX = rect.getWidthRatio(x)
        val rY = rect.getHeightRatio(y)
        val rangle = (theta).rotateVector(rX-0.5f,rY-0.5f)
        return sockType.getRect().any { it.contains(Point(rangle.first+0.5f, rangle.second+0.5f)) }
    }
}