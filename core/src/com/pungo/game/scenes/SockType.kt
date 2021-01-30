package com.pungo.game.scenes

import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.LcsVariable

enum class SockType {
    SMALL{
            override fun getWidth() = GetLcs.byPixel(70*rescale)
        override fun getHeight() = GetLcs.byPixel(132*rescale)
         },
    MEDIUM{
        override fun getWidth() = GetLcs.byPixel(96*rescale)
        override fun getHeight() = GetLcs.byPixel(188*rescale)
    },
    LARGE{
        override fun getWidth() = GetLcs.byPixel(120*rescale)
        override fun getHeight() = GetLcs.byPixel(244*rescale)
    };
    val rescale = 0.5f
    abstract fun getWidth(): LcsVariable
    abstract fun getHeight(): LcsVariable
}