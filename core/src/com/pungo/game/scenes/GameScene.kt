package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.game.Sock
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture
import kotlin.math.cos
import kotlin.math.sin

class GameScene : Scene("game", 0f, true)  {
    var score = 0
    val drumCentre = Pair(mainDistrict.block.cX,mainDistrict.block.cY)
    var radius = mainDistrict.block.height*0.4f
    val spawnCount = 12
    var theta = (0 until spawnCount).map {3.141f*2f*(it/spawnCount.toFloat()) }
    val spawnQueue = mutableListOf<Int>()
    var socks = mutableListOf<Sock>()
    var sockDrawer = mutableListOf<Sock>()
    init {
        for(i in 1..3){
            sockDrawer.add(Sock("L_$i",Gdx.files.internal("socks/L_$i.png"),SockType.LARGE) )
        }

        for(i in 1..2){
            sockDrawer.add(Sock("M_$i",Gdx.files.internal("socks/M_$i.png"),SockType.MEDIUM))
        }

        for(i in 1..5){
            sockDrawer.add(Sock("S_$i",Gdx.files.internal("socks/S_$i.png"),SockType.SMALL))
        }

    }


    override fun draw(batch: SpriteBatch) {
        super.draw(batch)
        socks.forEach {
            it.draw(batch)
        }
    }

    override fun update() {
        super.update()
        if(spawnQueue.size<5){
            addToQueue(true)
        }
        if(socks.size<3){
            val ind = spawnQueue.removeFirst()
            socks.add(
                sockDrawer.random().also {
                    it.relocate(theta[ind],radius,drumCentre)
                    it.modifyClickFunction {
                        socks.remove(it)
                    }
                }
            )
        }else{
            try {
                socks.forEach {
                    it.update()
                    it.touchHandler()
                }
            }catch (e: Exception){

            }

        }

    }

    fun addToQueue(unique: Boolean=false){
        try {
            spawnQueue.add(if(unique) (0 until 12).filter{it !in spawnQueue}.random() else (0 until 12).random() )
        }catch (e: Exception){

        }

    }


}