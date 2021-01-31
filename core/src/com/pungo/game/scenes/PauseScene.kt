package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.pungo.modules.audio.SfxPlayer
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture

class PauseScene(from: Scene): Scene("pause", 0f){

    init {

        mainDistrict.addFullPlot("bg").also {
            it.element = PinupImage("bg", SingleTexture(Gdx.files.internal("pause/pause_bg.png")))
        }

        mainDistrict.addFullPlot("resume", Rectangle(852f / 1280f, 1230f / 1280f, 258f / 720f, 380f / 720f)).also {
            it.element = SetButton("resume",SingleTexture(Gdx.files.internal("pause/resume_normal.png")),SingleTexture(Gdx.files.internal("pause/resume_pressed.png"))).also {
                it.clicked = {
                    SfxPlayer.play("click")
                    from.visible = true
                    LayerManager.scenesToRemove.add(this)
                    dispose()
                }
            }
        }

        mainDistrict.addFullPlot("menu", Rectangle(852f / 1280f, 1230f / 1280f, 136f / 720f, 258f / 720f)).also {
            it.element = SetButton("menu",SingleTexture(Gdx.files.internal("pause/main_normal.png")),SingleTexture(Gdx.files.internal("pause/main_pressed.png"))).also {
                it.clicked = {
                    SfxPlayer.play("click")
                    LayerManager.scenesToAdd.add(Pair(MenuScene(), true))
                    dispose()
                }
            }
        }

        mainDistrict.addFullPlot("exit", Rectangle(1026f / 1280f, 1230f / 1280f, 36f / 720f, 136f / 720f)).also {
            it.element = SetButton("exit",SingleTexture(Gdx.files.internal("pause/exit_normal.png")),SingleTexture(Gdx.files.internal("pause/exit_pressed.png"))).also{
                it.clicked = {
                    SfxPlayer.play("click")
                    dispose()
                    Gdx.app.exit()
                }
            }
        }

        SfxPlayer.addSFX("click", "SFX/click.ogg")

    }
}