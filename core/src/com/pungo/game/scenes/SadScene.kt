package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.uiElements.TextBox
import com.pungo.modules.visuals.textureHandling.SingleTexture

class SadScene(val finalScore: Int): Scene("sad",0f) {

    init {

        mainDistrict.addFullPlot("bg").also {
            it.element = PinupImage("bg",SingleTexture(Gdx.files.internal("ending/ending_bg.png")))
        }

        mainDistrict.addFullPlot("back", Rectangle(44f / 1280f, 214f / 1280f, 616f / 720f, 702f / 720f)).also {
            it.element = SetButton("back",
                SingleTexture(Gdx.files.internal("gallery/back_normal.png")),
                SingleTexture(Gdx.files.internal("gallery/back_pressed.png"))
            ).also { it2 ->
                it2.clicked = {
                    LayerManager.scenesToRemove.add(this)
                    LayerManager.scenesToAdd.add(Pair(MenuScene(), true))
                    dispose()
                }
            }
        }

        mainDistrict.addFullPlot("replay", Rectangle(1028f / 1280f, 1232f / 1280f, 32f / 720f, 132f / 720f))
            //replay tuşu

        //mainDistrict.addFullPlot("score").also {
        //    it.element = TextBox("score","$finalScore","font/MPLUSRounded1c-Black.ttf",36,colour= Color(1-223f/255f, 1-237f/255f, 1-240f/255f,1f))
        //}

        mainDistrict.addFullPlot("score",Rectangle(642f / 1280f, 932f / 1280f, 576 / 720f, 703f / 720f),z=40).also {
            it.element = PinupImage("score", SingleTexture(Gdx.files.internal("machine/score.png")))
        }
        //TODO score, z order canavar arkasında olacak

        mainDistrict.addFullPlot("score text",Rectangle(670f / 1280f, 890f / 1280f, 616f / 720f, 676f / 720f),z=40).also {
            it.element = TextBox("score","$finalScore","font/MPLUSRounded1c-Black.ttf",36,colour= Color(223f/255f, 237f/255f, 240f/255f,1f))
        }



    }
}