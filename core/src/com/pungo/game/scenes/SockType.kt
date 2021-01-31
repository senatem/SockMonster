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
            val l1 = mutableListOf(Pair(49f-hbo,138f+hbo),Pair(79f+hbo,138f+hbo),Pair(79f+hbo,68f-hbo),Pair(49f-hbo,68f-hbo))
            val l2 = mutableListOf(Pair(57f-hbo,89f+hbo),Pair(80f+hbo,78f+hbo),Pair(48f+hbo,22f-hbo),Pair(25f-hbo,36f-hbo))
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
            val l1 = mutableListOf(Pair(67f-hbo,198f+hbo),Pair(112f+hbo,198f+hbo),Pair(112f+hbo,96f-hbo),Pair(67f+hbo,96f-hbo))
            val l2 = mutableListOf(Pair(77f-hbo,121f+hbo),Pair(110f+hbo,101f+hbo),Pair(64f+hbo,24f-hbo),Pair(31f-hbo,44f-hbo))
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
            val l1 = mutableListOf(Pair(89f-hbo,242f+hbo),Pair(149f+hbo,242f+hbo),Pair(149f+hbo,104f-hbo),Pair(89f-hbo,104f-hbo))
            val l2 = mutableListOf(Pair(108f-hbo,147f+hbo),Pair(152f+hbo,121f+hbo),Pair(89f+hbo,11f-hbo),Pair(42f-hbo,37f-hbo))
            val r1 = ConvexPolygon(l1.map{Point(it.first/ow ,it.second/oh )}.toMutableList())
            val r2 = ConvexPolygon(l2.map{Point(it.first/ow ,it.second/oh )}.toMutableList())
            return listOf(r1,r2)
            //return listOf(r1)
        }
    },
        TEST{
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
    protected val hbo = 15f

    abstract fun getWidth(): LcsVariable
    abstract fun getHeight(): LcsVariable
    abstract fun getRect(): List<ConvexPolygon>
    abstract fun getScoreMult():Float
}