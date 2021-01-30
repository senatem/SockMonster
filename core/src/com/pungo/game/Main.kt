package com.pungo.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Cursor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.game.scenes.MenuScene
import com.pungo.game.scenes.GameScene
import com.pungo.game.scenes.PauseScene
import com.pungo.game.scenes.StartupScreen
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.scenes.LayerManager
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer


class Main : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    private val inputProcessor = SockMonsterEventListener()

    override fun create() {
        GetLcs.lcsInitialize()
        GetLcs.lcsInitialize(1280,720)
        Gdx.input.inputProcessor = inputProcessor

        batch = SpriteBatch()
        LayerManager.add(MenuScene(), true)
     //   addCursor()
    }

    private fun addCursor() {
        val pixmap = Pixmap(Gdx.files.internal("visuals/cursor32.png"))
        val xHotspot = pixmap.width / 2
        val yHotspot = pixmap.height / 2
        val cursor: Cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot)
        Gdx.graphics.setCursor(cursor)
        pixmap.dispose()
    }


    override fun render() {
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        LayerManager.update()

        batch.begin()
        LayerManager.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        LayerManager.dispose()
    }
}