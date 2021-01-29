package com.pungo.modules.visuals.textureHandling

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.basic.geometry.Rectangle
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.subTexture.ScalingType
import com.pungo.modules.visuals.subTexture.SubTexture
import kotlin.random.Random

/** Unlike the previous iterations this class does not directly concern itself with modifying individual sprites but has handlers for them
 * The macro management approach I believe will yield better results
 */
open class MultipleTexture(private var subTextures: MutableList<SubTexture> = mutableListOf()) : OmniVisual() {
    protected var activeSubTexture = 0
    var frameChanger = FrameChanger()

    /** Inverts the order of sub textures
     * damla özel fonksyonu
     */
    fun invertSubTextures() {
        subTextures.reverse()
    }


    fun addToSubTextures(st: SingleTexture) {
        subTextures.add(SubTexture(st.subTexture))
    }

    fun addToSubTextures(st: SubTexture) {
        subTextures.add(st)
    }

    fun addToSubTextures(st: List<SubTexture>) {
        subTextures.addAll(st)
    }

    fun setSubTextures(st: List<SubTexture>) {
        subTextures = st.toMutableList()
    }

    fun clearSubTextures() {
        subTextures.clear()
    }

    fun getSubTexture(no: Int?): SubTexture {
        return subTextures[no ?: activeSubTexture]
    }

    fun getFrameCopy(n: Int): SubTexture {
        return subTextures[n].copy()
    }


    override fun setScalingType(scalingType: ScalingType?, widthScaleFactor: Float?, heightScaleFactor: Float?) {
        subTextures.forEach {
            it.setScaling(scalingType, widthScaleFactor, heightScaleFactor)
        }
    }


    override fun draw(batch: SpriteBatch, alpha: Float) {
        subTextures[activeSubTexture].draw(batch, alpha, block)
    }

    override fun changeActiveSprite(ns: Int) {
        activeSubTexture = ns
    }

    override fun update() {
        frameChanger.update()
    }

    override fun recolour(c: Color) {
        subTextures.forEach {
            it.color = c
        }
    }

    override fun setClippingRect(r: Rectangle) {
        subTextures.forEach {
            it.clipRect = r
        }
    }

    /** Recolours a specific item
     * null recolours the active item
     */
    fun recolourItem(c: Color, itemNo: Int? = null) {
        subTextures[itemNo ?: activeSubTexture].color = c
    }

    fun changeVisualTypes() {
        subTextures[0].v
    }

    override fun copy(): OmniVisual {


        return MultipleTexture(subTextures.map { it.copy() }.toMutableList()).also { new ->
            when (frameChanger) {
                is FpsFrameChanger -> {
                    new.frameChanger = new.FpsFrameChanger((frameChanger as FpsFrameChanger).fps)
                }
                is SingleLoopFrameChanger -> {
                    new.frameChanger = new.SingleLoopFrameChanger((frameChanger as SingleLoopFrameChanger).fps)
                }
                is IdleActivator -> {
                    new.frameChanger = new.IdleActivator((frameChanger as IdleActivator).fps, (frameChanger as IdleActivator).goChance)
                }
            }
        }
    }

    override fun dispose() {

    }

    override fun setFlip(x: Boolean?, y: Boolean?, vFlipOrigin: Float?, hFlipOrigin: Float?) {
        subTextures.forEach {
            it.setFlip(x ?: it.isFlipX, y ?: it.isFlipY)
            it.setFlipOrigins(vFlipOrigin, hFlipOrigin)
        }
    }

    fun rollActiveFrame(n: Int) {
        activeSubTexture = (activeSubTexture + n).rem(subTextures.size)
    }


    /** This is the base class for frame changers
     * for any custom design, one can generate a frame changer class here
     * or inherit this from outside and make its own class, then inject to frameChanger of this object
     */
    open inner class FrameChanger {
        var loopDone = false
        open fun update() {

        }

        open fun start() {
        }
    }

    /** This generates a frame changer class with fps and changes frame with time
     *
     */
    inner class FpsFrameChanger(var fps: Float) : FrameChanger() {
        var time = 0f
        var active = true
        override fun update() {
            if (active) {
                time += Gdx.graphics.deltaTime
                rollActiveFrame((time * fps).toInt())
                time = time.rem(1 / fps)
            }
        }

        override fun start() {
            activeSubTexture = 0
            time = 0f
            loopDone = false
            active = true
        }

        fun deactivate() {
            active = false
            activeSubTexture = 0
        }
    }

    inner class SingleLoopFrameChanger(var fps: Float, var doneAtStart: Boolean = false) : FrameChanger() {
        var time = 0f

        init {
            if (doneAtStart) {
                activeSubTexture = subTextures.size - 1
                time = activeSubTexture / fps
            }
        }

        override fun update() {
            activeSubTexture = (time * fps).toInt().coerceAtMost(subTextures.size - 1)
            if (subTextures.size - 1 == activeSubTexture) {
                loopDone = true
            } else {
                time += Gdx.graphics.deltaTime

            }
        }

        override fun start() {
            activeSubTexture = 0
            time = 0f
            loopDone = false
        }
    }

    inner class IdleActivator(var fps: Float, var goChance: Float) : FrameChanger() {
        var time = 0f
        private var idleCounter = 0

        init {
            activeSubTexture = subTextures.size - 1

        }

        override fun update() {
            if (time * fps > 1) {
                if (subTextures.size - 1 == activeSubTexture) {
                    if (Random.nextFloat() < goChance * idleCounter) {
                        start()
                        idleCounter = 0
                    } else {
                        idleCounter += 1
                    }
                    loopDone = true
                } else {
                    activeSubTexture += 1
                }
                time -= 1f / fps
            }



            time += Gdx.graphics.deltaTime


        }

        override fun start() {
            activeSubTexture = 0
            time = 0f
            loopDone = false
        }
    }
}