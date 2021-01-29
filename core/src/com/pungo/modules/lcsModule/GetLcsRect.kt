package com.pungo.modules.lcsModule

import com.badlogic.gdx.Gdx

/** This is the getter for the data class, lcs rect
 *
 */
object GetLcsRect {
    /** calls a rectangle by borders
     * wStart and wEnd are width edges
     * hStart and hEnd are height edges
     */
    fun byBorders(wStart: LcsVariable, wEnd: LcsVariable, hStart: LcsVariable, hEnd: LcsVariable): LcsRect {
        val wStartIn = GetLcs.byLcs(wStart.asLcs().coerceAtMost(wEnd.asLcs()))
        val wEndIn = GetLcs.byLcs(wStart.asLcs().coerceAtLeast(wEnd.asLcs()))
        val hStartIn = GetLcs.byLcs(hStart.asLcs().coerceAtMost(hEnd.asLcs()))
        val hEndIn = GetLcs.byLcs(hStart.asLcs().coerceAtLeast(hEnd.asLcs()))
        val width = wEndIn - wStartIn
        val height = hEndIn - hStartIn
        val cX = (wEnd + wStart) / 2f
        val cY = (hEnd + hStart) / 2f
        return LcsRect(width, height, cX, cY, wStartIn, wEndIn, hStartIn, hEndIn)
    }

    /** calls a rectangle by parameters
     * width and height are width and height
     * cX and cY are centre x & y
     */
    fun byParameters(w: LcsVariable, h: LcsVariable, cX: LcsVariable = GetLcs.ofZero(), cY: LcsVariable = GetLcs.ofZero()): LcsRect {
        val wStart = cX - (w / 2f)
        val wEnd = cX + (w / 2f)
        val hStart = cY - (h / 2f)
        val hEnd = cY + (h / 2f)
        return LcsRect(w, h, cX, cY, wStart, wEnd, hStart, hEnd)
    }

    /** creates a 0 0 0 0 rectangle
     */
    fun ofZero(): LcsRect {
        val l = GetLcs.ofZero()
        return LcsRect(l, l, l, l, l, l, l, l)
    }

    fun ofFullScreen(): LcsRect {
        return byBorders(GetLcs.ofWidth(0f), GetLcs.ofWidth(1f), GetLcs.ofHeight(0f), GetLcs.ofHeight(1f))
    }

    fun ofCentreSquare(): LcsRect {
        return byParameters(GetLcs.byLcs(1f), GetLcs.byLcs(1f), GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
    }

    /** Yields the fitted rectangle to the displayed screen, this is not optimal
     */
    fun ofFittingRatioToScreen(): LcsRect {
        val t = byParameters(GetLcs.byPixel(Gdx.graphics.width), GetLcs.byPixel(Gdx.graphics.height), GetLcs.ofWidth(0.5f), GetLcs.ofHeight(0.5f))
        val t2 = t.getFittingRect(GetLcs.ofWidth(1f).asLcs(), GetLcs.ofHeight(1f).asLcs())
        val wr = GetLcs.ofWidth(1f) / t.width
        val hr = GetLcs.ofHeight(1f) / t.height
        return t2.resizeTo(wr, hr)
    }

}