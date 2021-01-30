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
            it.element = PinupImage("bg",SingleTexture(Gdx.files.internal("menu/bg_flat.png")))
        }

        mainDistrict.addFullPlot("go",Rectangle(852f / 1280f, 1230f / 1280f, 258f / 720f, 380f / 720f)).also {
            it.element = SetButton("go",SingleTexture(Gdx.files.internal("menu/play_normal.png")),SingleTexture(Gdx.files.internal("menu/play_pressed.png"))).also { it2->
                it2.clicked = {
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(GameScene(), true))
                    dispose()
                }
            }
        }

        mainDistrict.addFullPlot("gallery",Rectangle(852f / 1280f, 1230f / 1280f, 158f / 720f, 280f / 720f)).also {
            it.element = SetButton("gallery",SingleTexture(Gdx.files.internal("menu/gallery_normal.png")),SingleTexture(Gdx.files.internal("menu/gallery_pressed.png"))).also { it2->
                it2.clicked = {
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(GameScene(), true))
                    dispose()
                }
            }
        }

        mainDistrict.addFullPlot("exit",Rectangle(852f / 1280f, 1230f / 1280f, 58f / 720f, 180f / 720f)).also {
            it.element = SetButton("exit",SingleTexture(Gdx.files.internal("menu/exit_normal.png")),SingleTexture(Gdx.files.internal("menu/exit_pressed.png"))).also { it2->
                it2.clicked = {
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(GameScene(), true))
                    dispose()
                }
            }
        }
        MusicPlayer.open("MainThemeAlt.mp3")
        MusicPlayer.setLooping(true)
        // MusicPlayer.play()
    }
}