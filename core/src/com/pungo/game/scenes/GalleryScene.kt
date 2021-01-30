package com.pungo.game.scenes

import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.scenes.Scene

class GalleryScene: Scene("gallery",0f) {

    init {

        mainDistrict.addFullPlot("back", Rectangle(44f / 1280f, 214f / 1280f, 616f / 720f, 702f / 720f))

    }

}