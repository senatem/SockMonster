package com.pungo.game.scenes

import com.pungo.modules.basic.geometry.ConvexPolygon
import com.pungo.modules.basic.geometry.Point
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.LcsVariable

enum class SockType {
    SMALL{
        private val ow = 148f
        private val oh = 148f
        override fun getScoreMult(): Float {
            return 5f
        }
        override fun getWidth() = GetLcs.byPixel(ow*rescale)
        override fun getHeight() = GetLcs.byPixel(oh*rescale)
        override fun getRect(): List<ConvexPolygon> {
            val l1 = mutableListOf(Pair(49f,138f),Pair(79f,138f),Pair(79f,68f),Pair(49f,68f))
            val l2 = mutableListOf(Pair(57f,89f),Pair(80f,78f),Pair(48f,22f),Pair(25f,36f))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            val r2 = ConvexPolygon(l2.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            return listOf(r1,r2)

        }
         },
    MEDIUM{
        private val ow = 208f
        private val oh = 208f
        override fun getScoreMult(): Float {
            return 3f
        }
        override fun getWidth() = GetLcs.byPixel(ow*rescale)
        override fun getHeight() = GetLcs.byPixel(oh*rescale)
        override fun getRect(): List<ConvexPolygon> {
            val l1 = mutableListOf(Pair(67f,198f),Pair(112f,198f),Pair(112f,96f),Pair(67f,96f))
            val l2 = mutableListOf(Pair(77f,121f),Pair(110f,101f),Pair(64f,24f),Pair(31f,44f))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            val r2 = ConvexPolygon(l2.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            return listOf(r1,r2)
        }
    },
    LARGE{
        private val ow = 253f
        private val oh = 252f
        override fun getScoreMult(): Float {
            return 2f
        }
        override fun getWidth() = GetLcs.byPixel(ow*rescale)
        override fun getHeight() = GetLcs.byPixel(oh*rescale)
        override fun getRect(): List<ConvexPolygon> {
            val l1 = mutableListOf(Pair(89f,242f),Pair(149f,242f),Pair(149f,104f),Pair(89f,104f))
            val l2 = mutableListOf(Pair(108f,147f),Pair(152f,121f),Pair(89f,11f),Pair(42f,37f))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            val r2 = ConvexPolygon(l2.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            return listOf(r1,r2)
            //return listOf(r1)
        }
    },TEST{
        private val ow = 120f
        private val oh = 120f
        override fun getWidth() = GetLcs.byPixel(ow*rescale)
        override fun getHeight() = GetLcs.byPixel(oh*rescale)
        override fun getRect(): List<ConvexPolygon> {
            val l1 = mutableListOf(Pair(0f,60f),Pair(120f,60f),Pair(120f,120f),Pair(0f,120f))
            //val l2 = mutableListOf(Pair(69f,139f),Pair(113f,112f),Pair(47f,3f),Pair(3f,29f))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            //val r2 = ConvexPolygon(l2.map{Point(it.first/ow,it.second/oh)}.toMutableList())
            //return listOf(r1,r2)
            return listOf(r1)
        }

        override fun getScoreMult(): Float {
            return 1f
        }
    };


    val rescale = 0.75f
    abstract fun getWidth(): LcsVariable
    abstract fun getHeight(): LcsVariable
    abstract fun getRect(): List<ConvexPolygon>
    abstract fun getScoreMult():Float
}