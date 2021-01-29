package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.Color
import com.pungo.modules.visuals.PixmapGenerator
import com.pungo.modules.visuals.TwoVisuals
import com.pungo.modules.visuals.fromFont.BlockText
import com.pungo.modules.visuals.subTexture.ScalingType
import com.pungo.modules.visuals.textureHandling.SingleTexture


/** This object is used to generate ui objects without the hustle
 * Perfect for those who want to use, but not learn ui elements
 */
object FastGenerator {
    fun genericSetButton(id: String, text: String, textSize: Int, bgColour: Color, textColour: Color, fontPath: String, pressedColorRatio: Float = 0.5f, scalingType: ScalingType = ScalingType.FIT_ELEMENT): SetButton {
        val v12 = SingleTexture(PixmapGenerator.singleColour()).also {
            it.recolour(bgColour)
            it.setScalingType(scalingType)
        }
        val v22 = SingleTexture(PixmapGenerator.singleColour()).also {
            it.recolour(Color(bgColour.r * pressedColorRatio, bgColour.g * pressedColorRatio, bgColour.b * pressedColorRatio, bgColour.a))
            it.setScalingType(scalingType)
        }
        val v11 = BlockText(text, textSize, textColour, fontPath, scalingType = scalingType)
        val v21 = BlockText(text, textSize, Color(bgColour.r * pressedColorRatio, bgColour.g * pressedColorRatio, bgColour.b * pressedColorRatio, bgColour.a), fontPath, scalingType = scalingType)
        return SetButton(id, onVisual = TwoVisuals(v11, v12), offVisual = TwoVisuals(v21, v22))
    }

    fun colouredBox(id: String, colour: Color): PinupImage {

        return PinupImage(id, SingleTexture(PixmapGenerator.singleColour()).also {
            it.recolour(colour)
        })
    }
}