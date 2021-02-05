package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.pungo.modules.audio.SfxPlayer
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture

class ComicScene: Scene("Comic",0f) {
    val b1 = SetButton("first", SingleTexture(Gdx.files.internal("Story/Story.png"))).also {
        it.clicked = {
            SfxPlayer.play("click")
            mainDistrict.findPlot("full").element = b2
        }
    }

    val b2 = SetButton("second", SingleTexture(Gdx.files.internal("Story/Story2.png"))).also {
        it.clicked = {
            SfxPlayer.play("click")
            mainDistrict.findPlot("full").element = b3
        }
    }
    val b3 = SetButton("third", SingleTexture(Gdx.files.internal("Story/Story3.png"))).also {
        it.clicked = {
            SfxPlayer.play("click")
            LayerManager.scenesToRemove.add(this)
            LayerManager.scenesToAdd.add(Pair(GameScene(), true))
        }
    }


    init{
        mainDistrict.addFullPlot("full").also {
            it.element = b1
        }
    }
}