package com.pungo.modules.visuals.fromTiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.subTexture.ScalingType
import com.pungo.modules.visuals.subTexture.VisualSizeData

class TileRenderer(private var rowNo: Int, private var colNo: Int, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f) : OmniVisual() {
    var typesList = mutableListOf<Pair<String, OmniVisual>>()
    var mapData = mutableListOf<Tile>()
    private var singleWidth = GetLcs.byPixel(10f)
    private var singleHeight = GetLcs.byPixel(10f)
    var visualSizeData = VisualSizeData(GetLcsRect.byParameters(singleWidth * rowNo, singleHeight * colNo), scalingType, scaleFactor) //one interesting application of this bad boy is handling visuals for whatever
    // this happens when you write your own things


    override fun draw(batch: SpriteBatch, alpha: Float) {
        val c = visualSizeData.updateImageBlock(block)
        if (c) {
            singleWidth = (visualSizeData.imageBlock.width / colNo)
            singleHeight = (visualSizeData.imageBlock.height / rowNo)
            typesList.forEach {
                it.second.resize(singleWidth + GetLcs.byPixel(2f), singleHeight + GetLcs.byPixel(2f))
            }
        }
        mapData.forEach {
            typesList.first { it2 -> it2.first == it.type }.second.also { it2 ->
                it2.relocate(visualSizeData.imageBlock.wStart + singleWidth * (it.column + 0.5f), visualSizeData.imageBlock.hStart + singleHeight * (it.row + 0.5f))
                it2.draw(batch, alpha) //we dont use the SubBlock draw method cos it changes size and position, but we often only change the position
            }
        }
    }

    override fun changeActiveSprite(ns: Int) {}

    override fun update() {
        typesList.forEach {
            it.second.update()
        }
    }

    override fun recolour(c: Color) {
        typesList.forEach {
            it.second.recolour(c)
        }
    }

    override fun copy(): OmniVisual {
        return TileRenderer(rowNo, colNo)
    }

    override fun dispose() {
        typesList.forEach {
            it.second.dispose()
        }
    }

    override fun setFlip(x: Boolean?, y: Boolean?, vFlipOrigin: Float?, hFlipOrigin: Float?) {}
}