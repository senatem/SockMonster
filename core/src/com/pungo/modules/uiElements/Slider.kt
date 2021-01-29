package com.pungo.modules.uiElements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.lcsModule.GetLcs
import com.pungo.modules.lcsModule.GetLcsRect
import com.pungo.modules.lcsModule.LcsRect
import com.pungo.modules.visuals.OmniVisual
import com.pungo.modules.visuals.PixmapGenerator
import com.pungo.modules.visuals.textureHandling.SingleTexture

/**
 * horizontal false is vertical
 */
class Slider(id: String, resolution: IntRange = 0..100, private var horizontal: Boolean = true) : UiElement(id) {
    private var valRange = resolution.toList()
    private var resolution = valRange.size

    private var rail: OmniVisual = SingleTexture(PixmapGenerator.singleColour())
    private var knob: OmniVisual = SingleTexture(PixmapGenerator.singleColour())
    private var knobPosition = 1
    var knobPositionChangeFunction = { println("knob function ${valRange[knobPosition]}") }
    private var manipulated = false

    // override var block: LcsRect = GetLcsRect.ofZero()

    constructor(id: String, resolution: IntRange = 0..100, block: LcsRect, horizontal: Boolean) : this(id, resolution, horizontal) {
        district.block = block
        println("block: ${block.width.asPixel()} ${block.height.asPixel()}")
        rail = SingleTexture(PixmapGenerator.singleColour()).also {
            it.resize(block)
            //it.visualSize = VisualSize.FIT_ELEMENT
            it.recolour(Color.DARK_GRAY)
        }
        knob = if (horizontal) {
            SingleTexture(PixmapGenerator.singleColour()).also {
                it.reBlock(GetLcsRect.byParameters(block.width / (this.resolution), block.height))
                //it.setScalingType(scalingType = ScalingType.FIT_WITH_RATIO)
                it.recolour(Color.WHITE)
            }
        } else {
            SingleTexture(PixmapGenerator.singleColour()).also {
                it.reBlock(GetLcsRect.byParameters(block.width, block.height / (this.resolution)))
                //it.setScalingType(scalingType = ScalingType.FIT_WITH_RATIO)
                //it.visualSize = VisualSize.FIT_WITH_RATIO
                it.recolour(Color.WHITE)
            }
        }

    }

    constructor(id: String, resolution: IntRange = 0..100, block: LcsRect = GetLcsRect.ofZero(), rail: OmniVisual, knob: OmniVisual, horizontal: Boolean) : this(id, resolution, block, horizontal) {
        if (block.width.asLcs() == 0f) {
            district.block = GetLcsRect.byParameters(rail.block.width, rail.block.height, rail.block.cX, rail.block.cY)
        }
        this.rail = rail
        this.knob = knob

    }

    private fun updateKnobPosition() {
        val block = district.block
        if (horizontal) {
            knob.relocate(block.wStart + block.width / resolution * (knobPosition + 0.5f), block.cY)
        } else {
            knob.relocate(block.cX, block.hStart + block.height / resolution * (knobPosition + 0.5f))
        }
    }

    override fun touchHandler(mayTouch: Boolean): Boolean {

        if (hovering()) {
            if (Gdx.input.justTouched()) {
                manipulated = true
            }
            if (!Gdx.input.isTouched) {
                manipulated = false
            }
        } else {
            manipulated = false
        }
        if (manipulated) {
            val block = district.block

            if (horizontal) {
                val kp = ((GetLcs.ofX() - block.wStart).asLcs() / block.width.asLcs() * resolution).toInt()
                if (knobPosition != kp) {
                    knobPositionChangeFunction()
                    knobPosition = kp
                }

                knob.relocate(GetLcs.ofX().limitAbove(block.wEnd - knob.block.width / 2).limitBelow(block.wStart + knob.block.width / 2), block.cY)
            } else {
                val kp = ((GetLcs.ofY() - block.hStart).asLcs() / block.height.asLcs() * resolution).toInt()
                if (knobPosition != kp) {
                    knobPositionChangeFunction()
                    knobPosition = kp
                }
                knob.relocate(block.cX, GetLcs.ofY().limitAbove(block.hEnd - knob.block.height / 2).limitBelow(block.hStart + knob.block.height / 2))
            }
        }



        return false
    }

    override fun update() {
        if (!manipulated) {
            updateKnobPosition()
        }
    }

    /*
    override fun relocate(x: LcsVariable, y: LcsVariable) {
        block = GetLcsRect.byParameters(block.width, block.height, x, y)
        rail.relocate(x, y)
        //knob.relocate(x,y)

    }

    override fun resize(w: LcsVariable, h: LcsVariable) {
        block = GetLcsRect.byParameters(w, h, block.cX, block.cY)
        rail.resize(w, h)
        knob.resize(w, h)
    }

     */

    override fun draw(batch: SpriteBatch, alpha: Float) {
        rail.draw(batch, alpha)
        knob.draw(batch, alpha)
    }

    override fun dispose() {
        rail.dispose()
        knob.dispose()
    }

    override fun getValue(): Int {
        return valRange[knobPosition]
    }
}