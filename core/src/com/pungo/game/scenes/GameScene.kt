package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.game.Monster
import com.pungo.game.ScoreManager
import com.pungo.game.Sock
import com.pungo.game.SockMonsterCursor
import com.pungo.modules.audio.SfxPlayer
import com.pungo.modules.basic.geometry.Angle
import com.pungo.modules.basic.geometry.FastGeometry
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.FastGenerator
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.uiElements.TextBox
import com.pungo.modules.visuals.textureHandling.SingleTexture
import kotlin.random.Random
import kotlin.concurrent.schedule
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sin

class GameScene : Scene("game", 0f, true)  {
    var score = 0
    private val somethingCool = FastGenerator.colouredBox("sc",Color(0f,1f,0f,0.2f))
    var grabbyCounter = 0f

    private val drumCentre = Pair(mainDistrict.block.width*379/1280,mainDistrict.block.cY)
    private var radius = mainDistrict.block.height*0.3f
    private val spawnCount = 12
    var theta = (0 until spawnCount).map {3.141f*2f*(it/spawnCount.toFloat()) }
    private val filled = mutableListOf<Int>()
    private val socks = mutableListOf<Sock>()
    private val sockDrawer = mutableListOf<Sock>()
    private val looted = mutableListOf<Sock>()
    private val drumFreq = 0.1f
    private val baseSockSpeed = Angle(0.1f)
    private val monster = Monster()

    var testCounter = 0
    init {

        SfxPlayer.addSFX("click", "SFX/click.ogg")
        mainDistrict.addFullPlot("background",z=1).also {

            it.element = PinupImage("bg",SingleTexture(Gdx.files.internal("machine/game_bg.png")))
        }

        //mainDistrict.addFullPlot("bg",z=2).also {
        //    it.element = FastGenerator.colouredBox("bgc", Color.RED)
        //}

        mainDistrict.addFullPlot("drum",  Rectangle(36f/1280f,722f/1280f,17f/720f,703f/720f),z=20).also {
            it.element = PinupImage("drum",SingleTexture(Gdx.files.internal("machine/drum.png")))

        }

        mainDistrict.addFullPlot("bathroom", Rectangle(36f/1280f,722f/1280f,17f/720f,703f/720f),z=10).also {
            it.element = PinupImage("bathroom",SingleTexture(Gdx.files.internal("machine/bathroom.png")))
        }

        mainDistrict.addFullPlot("clothes", Rectangle(-261f/1280f,1019f/1280f,0f,1f), z=30).also {
            it.element = PinupImage("clothes",SingleTexture(Gdx.files.internal("machine/clothes.png"))).also {
                it.image.recolour(Color(1f,1f,1f,1f))
            }
        }

        mainDistrict.addFullPlot("monster",Rectangle(765f / 1280f, 1265f / 1280f, 10f / 720f, 710f / 720f),z=40).also {
            it.element = monster
        }

        mainDistrict.addFullPlot("monsterGlow",Rectangle(765f / 1280f, 1265f / 1280f, 10f / 720f, 710f / 720f),z=35).also {
            it.element = SetButton("mg",
                SingleTexture(Gdx.files.internal("Sock Monster Body parts/monster_glow.png")),
                SingleTexture(Gdx.files.internal("Sock Monster Body parts/monster_glow.png")).also {
                    it.recolour(Color(0.8f,0.8f,0.8f,1f))
                }).also {
                it.clicked = {
                    monster.saveToGallery("monster_json/attempt$testCounter")
                    testCounter++
                    monster.undress()
                    looted.clear()
                    score += 500
                    updateScoreboard()
                }
            }
            it.visible = false
        }

        mainDistrict.addFullPlot("score",Rectangle(642f / 1280f, 932f / 1280f, 576 / 720f, 703f / 720f),z=40).also {
            it.element = PinupImage("score", SingleTexture(Gdx.files.internal("machine/score.png")))
        }
        //TODO score, z order canavar arkasında olacak

        mainDistrict.addFullPlot("score text",Rectangle(670f / 1280f, 890f / 1280f, 616f / 720f, 676f / 720f),z=40).also {
            it.element = TextBox("score","0","font/MPLUSRounded1c-Black.ttf",36,colour=Color(223f/255f, 237f/255f, 240f/255f,1f))
        }
        //TODO score text

        mainDistrict.addFullPlot("back", Rectangle(14f / 1280f, 184f / 1280f, 616f / 720f, 702f / 720f), z=100).also {
            it.element = SetButton("back",
                SingleTexture(Gdx.files.internal("gallery/back_normal.png")),
                SingleTexture(Gdx.files.internal("gallery/back_pressed.png"))).also { it2 ->
                it2.clicked = {
                    SfxPlayer.play("click")
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(MenuScene(), true))
                    dispose()
                }
            }
        }


        for(i in 1..3){
            sockDrawer.add(Sock("L_$i",Gdx.files.internal("socks/L_$i.png"),SockType.LARGE) )
        }

        for(i in 1..4){
            sockDrawer.add(Sock("M_$i",Gdx.files.internal("socks/M_$i.png"),SockType.MEDIUM))
        }

        for(i in 1..5){
            sockDrawer.add(Sock("S_$i",Gdx.files.internal("socks/S_$i.png"),SockType.SMALL))
        }
    }

    /** Updates scoreboard
     *
     */
    private fun updateScoreboard(n: Int=score){
        (mainDistrict.findPlot("score text").element!! as TextBox).changeText(n.toString())
        when {
            score>=3000 -> {
                var maxNumbOfSocks = 30
            }
            score in 2000..2999 -> {
                var maxNumbOfSocks = 25
            }
            score in 1500..1999 -> {
                var maxNumbOfSocks = 20
            }
            score in 1000..1499 -> {
                var maxNumbOfSocks = 15
            }
            score in 500..999 -> {
                var maxNumbOfSocks = 10
            }
            else -> {
                var maxNumbOfSocks = 5
            }
        }
    }


    override fun draw(batch: SpriteBatch) {
        super.draw(batch)
        val drumSpeed = drumFreq*360f*Gdx.graphics.deltaTime
        ((mainDistrict.findPlot("drum").element as PinupImage).image as SingleTexture).subTexture.setOriginCenter()
        ((mainDistrict.findPlot("drum").element as PinupImage).image as SingleTexture).subTexture.rotate(-drumSpeed)
        ((mainDistrict.findPlot("clothes").element as PinupImage).image as SingleTexture).subTexture.rotate(-drumSpeed*0.9f)

        socks.forEach {
            it.speed = drumSpeed
            it.theta = it.theta + Angle(it.speed,Angle.Type.DEG)
        }
        socks.forEach {
            it.draw(batch)
        }
        //highlightClicks(batch)
    }



    override fun update() {
        super.update()
        var b = false
        socks.forEach {
            it.relocate(it.sockRad,drumCentre)
            if(it.relativeClick()){
                b = true
            }else{
                Gdx.graphics.setCursor(SockMonsterCursor.openCursor)
            }
        }
        if(b){
            grabby()
        }
        mainDistrict.findPlot("monsterGlow").visible = monster.clothedNo()==5
        (mainDistrict.findPlot("monsterGlow").element as SetButton).deactivate = monster.clothedNo()!=5

        // mainDistrict.findPlot("bg").element!!.visible = b

        if(socks.size<5){
            socks.add(
                sockDrawer.random().also {
                    val loc = (0 until spawnCount).filter{index -> index !in filled}.random()
                    filled.add(loc)
                      it.theta = Angle(((0..360).random()).toFloat(),Angle.Type.DEG)

                    when (it.sockType) {
                        // n is the range of the sock
                        SockType.LARGE -> {
                            val n = GetLcs.byPixel(195f + 5f * (Random.nextFloat() * 2 - 1))
                            it.relocate(n,drumCentre)
                            it.sockRad = n
                        }
                        SockType.MEDIUM -> {
                            val n = GetLcs.byPixel(225f + 15f * (Random.nextFloat() * 2 - 1))
                            it.relocate(n,drumCentre)
                            it.sockRad = n
                        }
                        else -> {
                            val n = GetLcs.byPixel(230f + 40f * (Random.nextFloat() * 2 - 1))
                            it.relocate(n,drumCentre)
                            it.sockRad = n
                        }
                    }
                    val speedList = listOf(0.001f, 0.003f, 0.005f, 0.009f, 0.02f, 0.05f)
                    it.speed = speedList.random()
                    it.modifyClickFunction {
                        if(it in looted) {
                            // game over
                            println(ScoreManager.listScores())
                            //score = 0
                            //updateScoreboard()
                            //looted.clear()
                            //socks.clear()
                            //filled.clear()
                            LayerManager.scenesToRemove.add(this)
                            LayerManager.scenesToAdd.add(Pair(SadScene(score), true))
                            dispose()
                        }
                        else {
                            socks.remove(it)
                            filled.remove(loc)
                            looted.add(it)
                            score+=(10f*it.sockType.getScoreMult()*significant(it.speed,1)).roundToInt()
                            updateScoreboard()
                            monster.wearSock(it.id)
                            /*
                            if (monster.clothedNo()==5) {
                                monster.saveToGallery("attempt$testCounter")
                                testCounter++
                                monster.undress()
                                looted.clear()
                                score += 500
                                updateScoreboard()
                            }

                             */
                        }
                    }
                }
            )
        }else{
            try {
                var mt = true
                socks.reversed().forEach {
                    it.update()
                    mt = !it.touchHandler(mt)
                }
            }catch (e: Exception){
                println("exception")

            }
        }
  /*          val gameTimer = Timer("gameTimer", true)
            gameTimer.schedule(10000) {
                if(score>=5) {
                    score -= 5
                    updateScoreboard()
                }
            }

*/

    }

    /* If this function is called, the hitbox of the socks are highlighted
     *
     */
    private fun highlightClicks(batch: SpriteBatch){
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

    private fun significant(x: Float, n: Int): Float {
        var res = x
        for (i in 0..n) {
            res *= 10
        }
        for (i in 0..n) {
            res /= 10
        }
        return res
    }

    private fun grabby(){
        grabbyCounter += Gdx.graphics.deltaTime
        if(grabbyCounter<0.2f){
            Gdx.graphics.setCursor(SockMonsterCursor.openCursor)
        }else{
            Gdx.graphics.setCursor(SockMonsterCursor.closedCursor)
        }
        grabbyCounter %= 0.4f
    }

    override fun keyDown(keycode: Int) {
        if (keycode == Input.Keys.ESCAPE) {
            LayerManager.scenesToAdd.add(Pair(PauseScene(this), true))
            this.visible = false
        }
    }


}