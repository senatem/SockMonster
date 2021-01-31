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
        val pixmap = Pixmap(Gdx.files.internal("ui/Hand open2.png"))
        val xHotspot = pixmap.width/4
        val yHotspot = pixmap.height/4
        pixmap.filter = Pixmap.Filter.NearestNeighbour
        pixmap.blending = Pixmap.Blending.None
        for (i in 0..pixmap.width){
            for (j in 0..pixmap.height){
                val ii = pixmap.getPixel(i,j)
                if(ii%256!=0){
                    pixmap.drawPixel(i,j,ii or 255)
                }
            }
        }
        openCursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot)


        val pixmap2 = Pixmap(Gdx.files.internal("ui/Hand closed2.png"))
        val xHotspot2 = pixmap2.width/4
        val yHotspot2 = pixmap2.height/4
        pixmap2.filter = Pixmap.Filter.NearestNeighbour
        pixmap2.blending = Pixmap.Blending.None
        for (i in 0..pixmap2.width){
            for (j in 0..pixmap2.height){
                val ii = pixmap2.getPixel(i,j)
                if(ii%256!=0){
                    pixmap2.drawPixel(i,j,ii or 255)
                }
            }
        }
        closedCursor = Gdx.graphics.newCursor(pixmap2, xHotspot2, yHotspot2)

    }

}