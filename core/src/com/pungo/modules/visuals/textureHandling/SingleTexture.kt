package com.pungo.modules.visuals.textureHandling

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.subTexture.ScalingType
import com.pungo.modules.visuals.subTexture.SubTexture

class SingleTexture : OmniVisual {
    var subTexture: SubTexture
        private set

    constructor(path: FileHandle, colour: Color = Color.WHITE, scalingType: ScalingType = ScalingType.FIT_ELEMENT, widthScaleFactor: Float = 1f, heightScaleFactor: Float = 1f) : super() {
        subTexture = SubTexture(TextureCache.openTexture(path)).also { st ->
            st.color = colour
            st.setScaling(scalingType, widthScaleFactor, heightScaleFactor)
            block = GetLcsRect.byParameters(GetLcs.byPixel(st.width), GetLcs.byPixel(st.height))
        }
    }

    constructor(sprite: SubTexture) : super() {
        subTexture = SubTexture(sprite)
        block = GetLcsRect.byParameters(GetLcs.byPixel(sprite.width), GetLcs.byPixel(sprite.height))
    }

    constructor(pxMap: Pixmap) : super() {
        val t = Texture(pxMap)
        subTexture = SubTexture(t)
        block = GetLcsRect.byParameters(GetLcs.byPixel(t.width), GetLcs.byPixel(t.height))
        pxMap.dispose()
        TextureCache.addToPixmapTextures(t)
    }


    override fun setScalingType(scalingType: ScalingType?, widthScaleFactor: Float?, heightScaleFactor: Float?) {
        subTexture.setScaling(scalingType, widthScaleFactor, heightScaleFactor)
    }


    override fun getOriginalRect(): LcsRect {
        return subTexture.visualSizeData.originalRect.copy()
    }

    override fun getImageRect(block: LcsRect?): LcsRect {
        subTexture.visualSizeData.updateImageBlock(block ?: this.block)
        return subTexture.visualSizeData.imageBlock

    }

    override fun setClippingRect(r: Rectangle) {
        subTexture.clipRect = r
    }


    override fun draw(batch: SpriteBatch, alpha: Float) {
        subTexture.draw(batch, alpha, block)
    }

    override fun changeActiveSprite(ns: Int) {}
    override fun update() {}

    override fun recolour(c: Color) {
        subTexture.color = c
    }

    override fun copy(): OmniVisual {
        return SingleTexture(subTexture).also {
            it.reBlock(block)
        }
    }

    override fun dispose() {
    }

    override fun setFlip(x: Boolean?, y: Boolean?, vFlipOrigin: Float?, hFlipOrigin: Float?) {
        subTexture.setFlip(x ?: subTexture.isFlipX, y ?: subTexture.isFlipY)
        subTexture.setFlipOrigins(vFlipOrigin, hFlipOrigin)
    }


}