package com.pungo.game.scenes

import com.pungo.modules.basic.geometry.ConvexPolygon
import com.pungo.modules.basic.geometry.Point
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.LcsVariable

enum class SockType {
    SMALL{
        private val ow = 70f
        private val oh = 132f
        override fun getScoreMult(): Float {
            return 5f
        }
        override fun getWidth() = GetLcs.byPixel(ow*rescale)
        override fun getHeight() = GetLcs.byPixel(oh*rescale)
        override fun getRect(): List<ConvexPolygon> {
            val l1 = mutableListOf(Pair(30f,122f),Pair(60f,122f),Pair(60f,52f),Pair(30f,52f))
            val l2 = mutableListOf(Pair(38f,73f),Pair(61f,59f),Pair(29f,6f),Pair(6f,20f))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            val r2 = ConvexPolygon(l2.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            return listOf(r1,r2)

        }
         },
    MEDIUM{
        private val ow = 96f
        private val oh = 188f
        override fun getScoreMult(): Float {
            return 3f
        }
        override fun getWidth() = GetLcs.byPixel(ow*rescale)
        override fun getHeight() = GetLcs.byPixel(oh*rescale)
        override fun getRect(): List<ConvexPolygon> {
            val l1 = mutableListOf(Pair(40f,178f),Pair(85f,178f),Pair(85f,76f),Pair(40f,76f))
            val l2 = mutableListOf(Pair(50f,101f),Pair(83f,81f),Pair(37f,4f),Pair(4f,24f))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            val r2 = ConvexPolygon(l2.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            return listOf(r1,r2)
        }
    },
    LARGE{
        private val ow = 120f
        private val oh = 244f
        override fun getScoreMult(): Float {
            return 2f
        }
        override fun getWidth() = GetLcs.byPixel(ow*rescale)
        override fun getHeight() = GetLcs.byPixel(oh*rescale)
        override fun getRect(): List<ConvexPolygon> {
            val l1 = mutableListOf(Pair(50f,234f),Pair(110f,234f),Pair(110f,96f),Pair(50f,96f))
            val l2 = mutableListOf(Pair(69f,139f),Pair(113f,112f),Pair(47f,3f),Pair(3f,29f))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            val r2 = ConvexPolygon(l2.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            return listOf(r1,r2)
        }
    };
    val rescale = 0.5f
    abstract fun getWidth(): LcsVariable
    abstract fun getHeight(): LcsVariable
    abstract fun getRect(): List<ConvexPolygon>
    abstract fun getScoreMult():Float
}