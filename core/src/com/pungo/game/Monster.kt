package com.pungo.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.pungo.modules.uiElements.MultiMediaItem
import com.pungo.modules.uiElements.PinupImage
import com.pungo.modules.visuals.textureHandling.SingleTexture

class Monster: MultiMediaItem("Monster") {
    val faces = MultiMediaItem("faces").also {
        it.addElement(PinupImage("1",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/1.png"))))
        it.addElement(PinupImage("2",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/2.png"))))
        it.addElement(PinupImage("3",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/3.png"))))
        it.addElement(PinupImage("4",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/4.png"))))
        it.addElement(PinupImage("5",SingleTexture(Gdx.files.internal("Sock Monster Body parts/face/5.png"))))
        it.invisibleExcept("1")
    }
    val leftArm = MultiMediaItem("leftArm").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_arm/default.png"))))
        WornSocks.leftArmList.forEach { it2->
            it.addElement(PinupImage(it2,SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_arm/$it2.png"))))
        }
        it.invisibleExcept("default")
    }

    val rightArm = MultiMediaItem("rightArm").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_arm/default.png"))))
        WornSocks.rightArmList.forEach { it2->
            it.addElement(PinupImage(it2,SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_arm/$it2.png"))))
        }
        it.invisibleExcept("default")
    }

    val rightLeg = MultiMediaItem("rightLeg").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_leg/default.png"))))
        WornSocks.rightLegList.forEach { it2->
            it.addElement(PinupImage(it2,SingleTexture(Gdx.files.internal("Sock Monster Body parts/right_leg/$it2.png"))))
        }
        it.invisibleExcept("default")
    }

    val leftLeg = MultiMediaItem("leftLeg").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_leg/default.png"))))
        WornSocks.leftLegList.forEach { it2->
            it.addElement(PinupImage(it2,SingleTexture(Gdx.files.internal("Sock Monster Body parts/left_leg/$it2.png"))))
        }
        it.invisibleExcept("default")
    }

    val tummy = MultiMediaItem("tummy").also {
        it.addElement(PinupImage("default",SingleTexture(Gdx.files.internal("Sock Monster Body parts/tummy/default.png"))))
        WornSocks.tummyList.forEach { it2->
            it.addElement(PinupImage(it2,SingleTexture(Gdx.files.internal("Sock Monster Body parts/tummy/$it2.png"))))
        }
        it.invisibleExcept("default")
    }

    init {
        addElement(PinupImage("sd",SingleTexture(Gdx.files.internal("Sock Monster Body parts/Shadow.png")).also {
            it.recolour(Color(1f,1f,1f,0.8f))
        }))
        addElement(faces)
        addElement(leftArm)
        addElement(rightArm)
        addElement(rightLeg)
        addElement(leftLeg)
        addElement(tummy)

    }

    /** Returns the number of clothes worn
     *
     */
    fun clothedNo(): Int {
        var clothes = 0
        listOf(leftArm,rightArm, leftLeg, rightLeg, tummy).forEach {
            if (!it.findElement("default")!!.visible){
                clothes += 1
            }
        }
        return clothes
    }

    fun undress(){
        listOf(leftArm,rightArm, leftLeg, rightLeg, tummy).forEach {
            it.invisibleExcept("default")
        }
    }

    /** Wears a sock by id
     *
     */
    fun wearSock(id: String){
        when (id) {
            in WornSocks.tummyList -> {
                tummy.invisibleExcept(id)
            }
            in WornSocks.leftLegList -> {
                leftLeg.invisibleExcept(id)
            }
            in WornSocks.rightLegList -> {
                rightLeg.invisibleExcept(id)
            }

            in WornSocks.leftArmList -> {
                leftArm.invisibleExcept(id)
            }
            in WornSocks.rightArmList -> {
                rightArm.invisibleExcept(id)
            }
            else -> {
                throw Exception("I cant wear $id")
            }
        }
        faces.invisibleExcept("${clothedNo()+1}")
        //clothedNo()
    }

    object WornSocks{
        val tummyList = listOf("M_1","S_4")
        val leftLegList =  listOf("L_2","S_1")
        val rightLegList = listOf("L_3","M_2")
        val leftArmList = listOf("S_2","S_3")
        val rightArmList = listOf("L_1","S_5")


    }
}