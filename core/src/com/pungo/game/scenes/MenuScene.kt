package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.pungo.modules.audio.MusicPlayer
import com.pungo.modules.audio.SfxPlayer
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.FastGenerator
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture

class MenuScene : Scene("menu", 0f, true) {
    init {
        mainDistrict.addFullPlot("bg").also {
            it.element = PinupImage("bg", SingleTexture(Gdx.files.internal("menu/bg_flat.png")))
        }

        mainDistrict.addFullPlot("go", Rectangle(852f / 1280f, 1230f / 1280f, 258f / 720f, 380f / 720f)).also {
            it.element = SetButton(
                "go",
                SingleTexture(Gdx.files.internal("menu/play_normal.png")),
                SingleTexture(Gdx.files.internal("menu/play_pressed.png"))
            ).also { it2 ->
                it2.clicked = {
                    SfxPlayer.play("click")
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(GameScene(), true))
                    MusicPlayer.stop()
                    dispose()
                }
            }
        }

        mainDistrict.addFullPlot("gallery", Rectangle(852f / 1280f, 1230f / 1280f, 136f / 720f, 258f / 720f)).also {
            it.element = SetButton(
                "gallery",
                SingleTexture(Gdx.files.internal("menu/gallery_normal.png")),
                SingleTexture(Gdx.files.internal("menu/gallery_pressed.png"))
            ).also { it2 ->
                it2.clicked = {
                    SfxPlayer.play("click")
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(GalleryScene(), true))
                    dispose()
                }
            }
        }

        mainDistrict.addFullPlot("exit", Rectangle(1026f / 1280f, 1230f / 1280f, 36f / 720f, 136f / 720f)).also {
            it.element = SetButton(
                "exit",
                SingleTexture(Gdx.files.internal("menu/exit_normal.png")),
                SingleTexture(Gdx.files.internal("menu/exit_pressed.png"))
            ).also { it2 ->
                it2.clicked = {
                    SfxPlayer.play("click")
                    dispose()
                    Gdx.app.exit()
                }
            }
        }

        mainDistrict.addFullPlot("credits", Rectangle(172f / 1280f, 428f / 1280f, 18f / 720f, 184f / 720f)).also {
            it.element = SetButton(
                "cred",
                FastGenerator.colouredBox("c1", Color(0f, 0f, 0f, 0f)),
                FastGenerator.colouredBox("c1", Color(0f, 0f, 0f, 0f))
            ).also {
                it.clicked = {
                    SfxPlayer.play("click")
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(CreditsScene(), true))
                    dispose()
                }
            }
        }

        SfxPlayer.addSFX("click", "SFX/click.ogg")
        MusicPlayer.open("SockMenu.mp3")
        MusicPlayer.setLooping(true)
        MusicPlayer.play()
    }
}