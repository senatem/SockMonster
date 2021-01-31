package com.pungo.game.scenes

import com.badlogic.gdx.Gdx
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.LayerManager
import com.pungo.modules.scenes.Scene
import com.pungo.modules.uiElements.SetButton
import com.pungo.modules.visuals.textureHandling.SingleTexture

class CreditsScene: Scene("credits",0f) {

    init {

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

    }

}