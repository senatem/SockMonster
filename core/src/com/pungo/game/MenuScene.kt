package com.pungo.game

import com.badlogic.gdx.Gdx
import com.pungo.modules.audio.MusicPlayer
import com.pungo.modules.audio.SfxPlayer
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.MultipleTexture
import com.pungo.modules.visuals.textureHandling.SingleTexture
import com.pungo.modules.visuals.textureHandling.TextureCache
import kotlin.random.Random

class MenuScene : Scene("menu", 0f, true) {
    private val bgs: MultipleTexture

    init {
        mainDistrict.addFullPlot("bg", Rectangle(0f, 1f, 0f, 1f)).also { it2 ->

            val v1 = TextureCache.subTextureFromPath(Gdx.files.internal("menu/1Artboard 1.png"))
            val v2 = TextureCache.subTextureFromPath(Gdx.files.internal("menu/2Artboard 1.png"))
            val v3 = TextureCache.subTextureFromPath(Gdx.files.internal("menu/3Artboard 1.png"))
            bgs = MultipleTexture(mutableListOf(v1, v2, v3)).also {
                it.frameChanger = it.FrameChanger()
            }

            it2.element = PinupImage("bg", bgs)
        }


        mainDistrict.splitToPlots("menu bit", Rectangle(1220f / 1920f, 1820f / 1920f, 700f / 1080f, 100f / 1080f), listOf(1260f, 720f, 200f, 720f, 200f, 720f, 200f, 720f, 1260f), listOf(120f, 170f, 20f, 170f, 120f), retainOriginal = true)
        mainDistrict.findPlot("menu bit").element = PinupImage("bb", SingleTexture(Gdx.files.internal("menu/buttonsbackground.png"))) //we add the bubble
        mainDistrict.superPlot("resume", mainDistrict.findPlot("menu bit_r2_c2"), mainDistrict.findPlot("menu bit_r2_c4"), 10).also { it2 ->

            it2.element = SetButton("resume", SingleTexture(Gdx.files.internal("menu/resume_normal.png")), SingleTexture(Gdx.files.internal("menu/resume_hover.png"))).also {
                it.setInactiveVisual(SingleTexture(Gdx.files.internal("menu/resume_neg.png")))
                it.clicked = {
                    //TODO resume
                    MusicPlayer.stop()
                }
            }
        }
        mainDistrict.superPlot("play", mainDistrict.findPlot("menu bit_r4_c2"), mainDistrict.findPlot("menu bit_r4_c4"), 10).also { it2 ->
            it2.element = SetButton("play", SingleTexture(Gdx.files.internal("menu/play_normal.png")), SingleTexture(Gdx.files.internal("menu/play_hover.png"))).also { it3 ->
                it3.clicked = {
                    SfxPlayer.play("click")
                    LayerManager.scenesToRemove.add(this)
                    dispose()
                }
            }
        }
        mainDistrict.superPlot("credits", mainDistrict.findPlot("menu bit_r6_c2"), mainDistrict.findPlot("menu bit_r6_c4"), 10).also { it2 ->
            it2.element = SetButton("credits", SingleTexture(Gdx.files.internal("menu/credits_normal.png")), SingleTexture(Gdx.files.internal("menu/credits_hover.png"))).also {
                it.clicked = {
                    SfxPlayer.play("click")
                    this.visible = false
                }
            }
        }
        mainDistrict.findPlot("menu bit_r8_c2").element = SetButton("options", SingleTexture(Gdx.files.internal("menu/options_normal.png")), SingleTexture(Gdx.files.internal("menu/options_hover.png"))).also {
            it.clicked = {
                SfxPlayer.play("click")
                this.visible = false
            }
        }
        mainDistrict.findPlot("menu bit_r8_c4").element = SetButton("exit", SingleTexture(Gdx.files.internal("menu/exit_normal.png")), SingleTexture(Gdx.files.internal("menu/exit_hover.png"))).also {
            it.clicked = {
                SfxPlayer.play("click")
                dispose()
                Gdx.app.exit()
            }
        }
        SfxPlayer.addSFX("click", "SFX/click.ogg")
        MusicPlayer.open("Music/ButterflySong.mp3")
        MusicPlayer.setLooping(true)
        MusicPlayer.play()
    }

    override fun entering() {
        bgs.changeActiveSprite(Random.nextInt(0, 3))
    }
}