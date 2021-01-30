package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.game.ScoreManager
import com.pungo.game.Sock
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.FastGenerator
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture
import kotlin.math.cos
import kotlin.math.sin

class GameScene : Scene("game", 0f, true)  {
    var score = 0

    private val drumCentre = Pair(mainDistrict.block.cX,mainDistrict.block.cY)
    private var radius = mainDistrict.block.height*0.4f
    private val spawnCount = 12
    var theta = (0 until spawnCount).map {3.141f*2f*(it/spawnCount.toFloat()) }
    // private val spawnQueue = mutableListOf<Int>()
    private val filled = mutableListOf<Int>()
    private val socks = mutableListOf<Sock>()
    private val sockDrawer = mutableListOf<Sock>()
    private val looted = mutableListOf<Sock>()
    private val drumSpeed = 0.4f
    var testCounter = 0
    init {
        mainDistrict.addFullPlot("bg").also {
            it.element = FastGenerator.colouredBox("bgc", Color.RED)
        }

        mainDistrict.addFullPlot("drum",z=20).also {
            it.element = PinupImage("drum",SingleTexture(Gdx.files.internal("machine/drum.png")))
        }

        mainDistrict.addFullPlot("bathroom",z=10).also {
            it.element = PinupImage("bathroom",SingleTexture(Gdx.files.internal("machine/bathroom.png")))
        }

        mainDistrict.addFullPlot("clothes",z=30).also {
            it.element = PinupImage("clothes",SingleTexture(Gdx.files.internal("machine/clothes.png")))
        }

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
        ((mainDistrict.findPlot("drum").element as PinupImage).image as SingleTexture).subTexture.rotate(-drumSpeed*1.2f)
        ((mainDistrict.findPlot("clothes").element as PinupImage).image as SingleTexture).subTexture.rotate(-drumSpeed)
        socks.forEach {
            it.draw(batch)
        }
    }

    override fun update() {
        super.update()
        var b = false
        socks.forEach {
            if(it.relativeClick()){
                b = true
            }
        }
        mainDistrict.findPlot("bg").element!!.visible = b

        if(socks.size<3){
        //    val ind = spawnQueue.removeFirst()
            socks.add(
                sockDrawer.random().also {
        //            it.relocate(theta[ind],radius,drumCentre)
                    val loc = (0 until spawnCount).filter{index -> index !in filled}.random()
                    filled.add(loc)
                    it.theta = 3.141f*2f*(loc/spawnCount.toFloat())
                    it.relocate(it.theta,radius,drumCentre)
                    it.modifyClickFunction {
                        if(it in looted) {
                            // game over
                            ScoreManager.newScore("Not Implemented$testCounter", score)
                            println(ScoreManager.listScores())
                            score = 0
                            testCounter++
                            looted.clear()
                            socks.clear()
                            filled.clear()
                        }
                        else {
                            socks.remove(it)
                            filled.remove(loc)
                            looted.add(it)
                            score++
                        }
                    }
                }
            )
        }else{
            try {
                var mt = true
                socks.forEach {
                    it.theta = it.theta + 0.02f
                    it.relocate(it.theta,radius,drumCentre)
                    it.update()
                    mt = !it.touchHandler(mt)
                }
            }catch (e: Exception){

            }

        }

    }
/*
    fun addToQueue(unique: Boolean=false){
        try {
            spawnQueue.add(if(unique) (0 until spawnCount).filter{it !in spawnQueue}.random() else (0 until spawnCount).random() )
        }catch (e: Exception){

        }
    }

 */
}