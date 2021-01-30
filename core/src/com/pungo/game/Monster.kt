package com.pungo.game

import com.badlogic.gdx.Gdx
import com.pungo.modules.uiElements.MultiMediaItem
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.visuals.textureHandling.SingleTexture

class Monster: MultiMediaItem("Monster") {
    val faces = MultiMediaItem("faces").also {
        it.addElement(PinupImage("head1",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/1.png"))))
        it.addElement(PinupImage("head2",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/2.png"))))
        it.addElement(PinupImage("head3",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/3.png"))))
        it.addElement(PinupImage("head4",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/4.png"))))
        it.addElement(PinupImage("head5",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/5.png"))))
        it.invisibleExcept("head1")
    }
    val leftArm = MultiMediaItem("leftArm").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_arm/default.png"))))
        it.addElement(PinupImage("S_2",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_arm/S_2.png"))))
        it.addElement(PinupImage("S_3",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_arm/S_3.png"))))
        it.invisibleExcept("default")
    }

    val rightArm = MultiMediaItem("rightArm").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_arm/default.png"))))
        it.addElement(PinupImage("L_1",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_arm/L_1.png"))))
        it.addElement(PinupImage("S_5",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_arm/S_5.png"))))
        it.invisibleExcept("default")
    }

    val rightLeg = MultiMediaItem("rightLeg").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_leg/default.png"))))
        it.addElement(PinupImage("L_3",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_leg/L_3.png"))))
        it.addElement(PinupImage("M_2",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_leg/M_2.png"))))
        it.invisibleExcept("default")
    }

    val leftLeg = MultiMediaItem("leftLeg").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_leg/default.png"))))
        it.addElement(PinupImage("L_2",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_leg/L_2.png"))))
        it.addElement(PinupImage("S_1",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_leg/S_1.png"))))
        it.invisibleExcept("default")
    }

    val tummy = MultiMediaItem("tummy").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/tummy/default.png"))))
        it.addElement(PinupImage("M_1",SingleTexture(Gdx.files.internal("Sock Monster Body parts/tummy/M_1.png"))))
        it.addElement(PinupImage("S_4",SingleTexture(Gdx.files.internal("Sock Monster Body parts/tummy/S_4.png"))))
        it.invisibleExcept("default")
    }

    init {
        addElement(PinupImage("sd",SingleTexture(Gdx.files.internal("Sock Monster Body parts/Shadow.png"))))
        addElement(faces)
        addElement(leftArm)
        addElement(rightArm)
        addElement(rightLeg)
        addElement(leftLeg)
        addElement(tummy)

    }
}