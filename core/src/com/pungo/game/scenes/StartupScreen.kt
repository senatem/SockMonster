package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.pungo.modules.basic.Jukebox
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.visuals.textureHandling.SingleTexture

class StartupScreen: Scene("startup", 0f, true)  {
    val jb  = Jukebox(resetOnFirstCall = true)
    val st = SingleTexture(Gdx.files.internal("loading/PunGine.png"))
    val st2 = SingleTexture(Gdx.files.internal("loading/PunGo.png"))
    var t = 0f
    val app_time = 3f

    init {
        mainDistrict.addFullPlot("bg").also {
            it.element = PinupImage("bg",st)
        }

        mainDistrict.addFullPlot("bg2").also {
            it.element = PinupImage("bg2",st2)
        }

        jb.addEvent(app_time*2) {
            LayerManager.scenesToRemove.add(this)
            LayerManager.scenesToAdd.add(Pair(MenuScene(), true))
            dispose()
        }
    }

    override fun update() {
        super.update()
        jb.update()
        t += Gdx.graphics.deltaTime
        val a = if(t<app_time*0.5){
            t.coerceAtMost(1f)
        }else{
            1f-(t-app_time+1).coerceIn(0f..1f)
        }
        val b = if(t<app_time*1.5){
            (t-app_time).coerceIn(0f..1f)
        }else{
            1f-(t-app_time*2+1).coerceIn(0f..1f)
        }
        st.recolour(Color(1f,1f,1f,a))
        st2.recolour(Color(1f,1f,1f,b))
    }
}