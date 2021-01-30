package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.game.ScoreManager
import com.pungo.game.Sock
import com.pungo.modules.basic.geometry.Angle
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.FastGenerator
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.visuals.textureHandling.SingleTexture
import kotlin.math.sin

class GameScene : Scene("game", 0f, true)  {
    var score = 0
    private val somethingCool = FastGenerator.colouredBox("sc",Color.CHARTREUSE)

    private val drumCentre = Pair(mainDistrict.block.width*379/1280,mainDistrict.block.cY)
    private var radius = mainDistrict.block.height*0.3f
    private val spawnCount = 12
    var theta = (0 until spawnCount).map {3.141f*2f*(it/spawnCount.toFloat()) }
    // private val spawnQueue = mutableListOf<Int>()
    private val filled = mutableListOf<Int>()
    private val socks = mutableListOf<Sock>()
    private val sockDrawer = mutableListOf<Sock>()
    private val looted = mutableListOf<Sock>()
    //private val drumSpeed = 0.4f
    private val drumFreq = 0.1f
    private val baseSockSpeed = Angle(0.1f)
    var testCounter = 0
    init {
        mainDistrict.addFullPlot("background",z=1).also {

            it.element = PinupImage("bg",SingleTexture(Gdx.files.internal("machine/bg_placeholder.png")))
        }

        mainDistrict.addFullPlot("bg",z=2).also {
            it.element = FastGenerator.colouredBox("bgc", Color.RED)
        }

        mainDistrict.addFullPlot("drum",  Rectangle(36f/1280f,722f/1280f,17f/720f,703f/720f),z=20).also {
            it.element = PinupImage("drum",SingleTexture(Gdx.files.internal("machine/drum.png")))

        }

        mainDistrict.addFullPlot("bathroom", Rectangle(36f/1280f,722f/1280f,17f/720f,703f/720f),z=10).also {
            it.element = PinupImage("bathroom",SingleTexture(Gdx.files.internal("machine/bathroom.png")))
        }

        mainDistrict.addFullPlot("clothes", Rectangle(-261f/1280f,1019f/1280f,0f,1f), z=30).also {
            it.element = PinupImage("clothes",SingleTexture(Gdx.files.internal("machine/clothes.png"))).also {
                it.image.recolour(Color(1f,1f,1f,0.3f))
            }
        }


        for(i in 1..3){
            //sockDrawer.add(Sock("L_$i",Gdx.files.internal("badlogic.jpg"),SockType.TEST))
            sockDrawer.add(Sock("L_$i",Gdx.files.internal("socks/L_$i.png"),SockType.LARGE) )
        }
/*
        for(i in 1..2){
            sockDrawer.add(Sock("M_$i",Gdx.files.internal("socks/M_$i.png"),SockType.MEDIUM))
        }

        for(i in 1..5){
            //sockDrawer.add(Sock("S_$i",Gdx.files.internal("socks/S_$i.png"),SockType.SMALL))
            sockDrawer.add(Sock("S_$i",Gdx.files.internal("badlogic.jpg"),SockType.SMALL))
        }

 */

    }


    override fun draw(batch: SpriteBatch) {
        super.draw(batch)
        val drumSpeed = drumFreq*360f*Gdx.graphics.deltaTime
        ((mainDistrict.findPlot("drum").element as PinupImage).image as SingleTexture).subTexture.setOriginCenter()
        ((mainDistrict.findPlot("drum").element as PinupImage).image as SingleTexture).subTexture.rotate(-drumSpeed)
        ((mainDistrict.findPlot("clothes").element as PinupImage).image as SingleTexture).subTexture.rotate(-drumSpeed*0.9f)
        socks.forEach {
            //it.theta = it.theta + baseSockSpeed*Gdx.graphics.deltaTime
            it.theta = it.theta + Angle(drumSpeed,Angle.Type.DEG)
        }
        socks.forEach {
            it.draw(batch)
        }
        for (i in 0..200){
            for (j in 0..200){
                val p = mainDistrict.block.getPointFromRatio(i/200f,j/200f)
                socks.forEach {
                    if(it.relativeClick(p.first,p.second)){
                        somethingCool.resize(mainDistrict.block.width/200,mainDistrict.block.height/200)
                        somethingCool.relocate(p.first,p.second)
                        somethingCool.draw(batch)
                    }

                }

            }
        }




    }

    override fun update() {
        super.update()
        var b = false
        socks.forEach {
            it.relocate(radius,drumCentre)
            if(it.relativeClick()){
                b = true
            }
        }
        mainDistrict.findPlot("bg").element!!.visible = b

        if(socks.size<1){
            socks.add(
                sockDrawer.random().also {
                    val loc = (0 until spawnCount).filter{index -> index !in filled}.random()
                    filled.add(loc)
                    val speedList = listOf(0.001f, 0.003f, 0.005f, 0.009f, 0.02f, 0.05f)
                    it.speed = speedList.random()
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
                    it.update()
                    mt = !it.touchHandler(mt)
                }
            }catch (e: Exception){
                println("exception")

            }

        }


    }
/*
    fun addToQueue(unique: Boolean=false){
        try {
            spawnQueue.add(if(unique) (0 until spawnCount).filter{it !in spawnQueue}.random() else (0 until spawnCount).random() )
        }catch (e: Exception){
            } catch (e: Exception){

        }
    }

 */
}