package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.pungo.game.ScoreManager
import com.pungo.modules.audio.MusicPlayer
import com.pungo.modules.audio.SfxPlayer
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.FastGenerator
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.MultipleTexture
import com.pungo.modules.visuals.textureHandling.SingleTexture
import com.pungo.modules.visuals.textureHandling.TextureCache
import kotlin.random.Random

class MenuScene : Scene("menu", 0f, true) {
    init {
        mainDistrict.addFullPlot("bg").also {
            it.element = PinupImage("bg",SingleTexture(Gdx.files.internal("sm.png")))
        }

        mainDistrict.addFullPlot("go",Rectangle(0.4f,0.6f,0.2f,0.3f)).also {
            it.element = SetButton("go",FastGenerator.colouredBox("c1", Color.YELLOW),FastGenerator.colouredBox("c1", Color.RED)).also { it2->
                it2.clicked = {
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(GameScene(), true))
                    dispose()
                }
            }
        }
        MusicPlayer.open("MainThemeAlt.mp3")
        MusicPlayer.setLooping(true)
        MusicPlayer.play()
    }
}