package com.pungo.game

import com.badlogic.gdx.Gdx
import com.pungo.modules.uiElements.MultiMediaItem
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.visuals.textureHandling.SingleTexture

class Monster: MultiMediaItem("Monster") {
    val faces = MultiMediaItem("faces").also {
        it.addElement(PinupImage("head",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/1.png"))))
        it.addElement(PinupImage("head",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/2.png"))))
        it.addElement(PinupImage("head",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/3.png"))))
        it.addElement(PinupImage("head",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/4.png"))))
        it.addElement(PinupImage("head",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/5.png"))))
    }
    val leftArm = MultiMediaItem("leftArm").also {
        //it.addElement(PinupImage("head",SingleTexture(Gdx.files.internal("Sock Monster Body parts/Face 1.png"))))
        //it.addElement(PinupImage("head",SingleTexture(Gdx.files.internal("Sock Monster Body parts/Face 1.png"))))
    }
    init {
        addElement(faces)


    }
}