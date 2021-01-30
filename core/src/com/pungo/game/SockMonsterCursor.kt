package com.pungo.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Cursor
import com.badlogic.gdx.graphics.Pixmap

object SockMonsterCursor {
    val openCursor : Cursor
    val closedCursor: Cursor

    init {
        val pixmap = Pixmap(Gdx.files.internal("ui/Hand open.png"))
        val xHotspot = pixmap.width/4
        val yHotspot = pixmap.height/4
        openCursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot)

        val pixmap2 = Pixmap(Gdx.files.internal("ui/Hand closed.png"))
        val xHotspot2 = pixmap.width/4
        val yHotspot2 = pixmap.height/4
        closedCursor = Gdx.graphics.newCursor(pixmap2, xHotspot2, yHotspot2)
    }

}