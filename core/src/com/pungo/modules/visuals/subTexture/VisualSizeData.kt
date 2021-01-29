package com.pungo.modules.visuals.subTexture

import com.pungo.modules.lcsModule.LcsRect

data class VisualSizeData(val originalRect: LcsRect, val scalingType: ScalingType = ScalingType.FIT_ELEMENT, val widthScaleFactor: Float = 1f, val heightScaleFactor: Float = 1f) {
    var imageBlock: LcsRect = originalRect


    private fun fitElement(block: LcsRect): LcsRect {
        return imageBlock.resizeTo(block.width * widthScaleFactor, block.height * heightScaleFactor)
    }

    private fun fitWithRatio(block: LcsRect): LcsRect {
        block.getFittingRect(originalRect.width.asLcs(), originalRect.height.asLcs()).also {
            return imageBlock.resizeTo(it.width * widthScaleFactor, it.height * heightScaleFactor)
        }
    }

    private fun fitStatic(): LcsRect {
        return imageBlock.resizeTo(originalRect.width * widthScaleFactor, originalRect.height * heightScaleFactor)
    }

    fun updateImageBlock(block: LcsRect): Boolean {
        val ib = imageBlock.copy()
        imageBlock = when (scalingType) {
            ScalingType.STATIC -> {
                fitStatic()
            }
            ScalingType.FIT_ELEMENT -> {
                fitElement(block)
            }
            ScalingType.FIT_WITH_RATIO -> {
                fitWithRatio(block)
            }
        }.relocateTo(block.cX, block.cY)
        return ib != imageBlock
    }
}