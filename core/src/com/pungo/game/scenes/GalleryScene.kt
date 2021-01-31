package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.pungo.game.Monster
import com.pungo.modules.audio.MusicPlayer
import com.pungo.modules.audio.SfxPlayer
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class GalleryScene: Scene("gallery",0f) {
    val galleryMonsters = mutableListOf(listOf("default","default","default","default","default"))
    var index = 0

    init {
         val v = loadMonster("monster_json/attempt0")
         println(v)
        mainDistrict.addFullPlot("bg").also {
            it.element = PinupImage("bg",SingleTexture(Gdx.files.internal("gallery/gallery_bg.png")))
        }

        mainDistrict.addFullPlot("monster",Rectangle(765f / 1280f, 1265f / 1280f, 10f / 720f, 710f / 720f),z=40).also {
            it.element = Monster()
        }

        mainDistrict.addFullPlot("back", Rectangle(44f / 1280f, 214f / 1280f, 616f / 720f, 702f / 720f)).also {
            it.element = SetButton("back",
                SingleTexture(Gdx.files.internal("gallery/back_normal.png")),
                SingleTexture(Gdx.files.internal("gallery/back_pressed.png"))).also {it2 ->
                    it2.clicked = {
                        SfxPlayer.play("click")
                        LayerManager.scenesToRemove.add(this)
                        LayerManager.scenesToAdd.add(Pair(MenuScene(), true))
                        dispose()
                }
            }
        }

        mainDistrict.addFullPlot("left", Rectangle(36f / 1280f, 100f / 1280f, 231f / 720f, 349f / 720f)).also {
            it.element = SetButton("left",
                SingleTexture(Gdx.files.internal("gallery/left_normal.png")),
                SingleTexture(Gdx.files.internal("gallery/left_pressed.png"))).also {
                    it.clicked = {
                        index -= 1
                        if(index <=0){
                            index = galleryMonsters.size-1
                        }
                    }
            }
        }

        mainDistrict.addFullPlot("right", Rectangle(1180f / 1280f, 1244f / 1280f, 231f / 720f, 349f / 720f)).also {
            it.element = SetButton("right",
                SingleTexture(Gdx.files.internal("gallery/right_normal.png")),
                SingleTexture(Gdx.files.internal("gallery/right_pressed.png"))).also {
                it.clicked = {
                    index += 1
                    if(index>= galleryMonsters.size){
                        index = 0
                    }
                }
            }
        }

        mainDistrict.addFullPlot("title", Rectangle(826f / 1280f, 1098f / 1280f, 644f / 720f, 698f / 720f))
        //burası başlık olacak galerideki resimlere

        mainDistrict.addFullPlot("image", Rectangle(164f / 1280f, 1118f / 1280f, 48f / 720f, 576f / 720f))
        SfxPlayer.addSFX("click", "SFX/click.ogg")

    }

    fun loadMonster(path: String) : Map<String, String> {
        val json = Json { isLenient=true }
        val file = Gdx.files.internal(path)
        val res = json.decodeFromString<Map<String, String>>(file.readString())
        return res
    }

}