package com.pungo.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Cursor
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture

object SockMonsterCursor {
    lateinit var openCursor : Cursor
    lateinit var closedCursor: Cursor

    init {



    }

    fun that(){
        val pixmap = Pixmap(Gdx.files.internal("ui/Hand.png"))
        val xHotspot = pixmap.width/4
        val yHotspot = pixmap.height/4
        pixmap.filter = Pixmap.Filter.NearestNeighbour
        pixmap.blending = Pixmap.Blending.None
        for (i in 0..pixmap.width){
            for (j in 0..pixmap.height){
                val ii = pixmap.getPixel(i,j)
                val by = (ii/256)%256
                val by2 = (ii/256/256)%256
                if((by in -216..-116)&&(by2 in -121..-21)){
                    pixmap.drawPixel(i,j,0)
                }
            }
        }
        openCursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot)


        val pixmap2 = Pixmap(Gdx.files.internal("ui/Hand 2.png"))
        val xHotspot2 = pixmap2.width/4
        val yHotspot2 = pixmap2.height/4
        pixmap2.filter = Pixmap.Filter.NearestNeighbour
        pixmap2.blending = Pixmap.Blending.None
        for (i in 0..pixmap2.width){
            for (j in 0..pixmap2.height){
                val ii = pixmap2.getPixel(i,j)
                val by = (ii/256)%256
                val by2 = (ii/256/256)%256
                if((by in -216..-116)&&(by2 in -121..-21)){
                    pixmap2.drawPixel(i,j,0)
                }
            }
        }
        closedCursor = Gdx.graphics.newCursor(pixmap2, xHotspot2, yHotspot2)

    }

}