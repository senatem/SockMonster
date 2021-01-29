package com.pungo.modules.lcsModule

import com.badlogic.gdx.Gdx

/** GetLcs object is used to create LcsVariables
 * It must be initialized in the create() function of the Main class before use
 */
object GetLcs {
    private var initialWidth = 0f // with of the screen on loading
    private var initialHeight = 0f // height of the screen on loading
    var lcsCoeff = 0f// a side of a LCS square

    fun lcsInitialize(w: Int? = null, h: Int? = null) {
        initialWidth = (w ?: Gdx.graphics.width).toFloat()
        initialHeight = (h ?: Gdx.graphics.height).toFloat()
        lcsCoeff = initialWidth.coerceAtMost(initialHeight)
    }

    /** Creates a variable using a LCS value
     */
    fun byLcs(n: Float): LcsVariable {
        return LcsVariable(n, lcsCoeff)
    }

    fun byPixel(n: Int): LcsVariable {
        return byPixel(n.toFloat())
    }

    /** Creates a variable using a pixel value
     */
    fun byPixel(n: Float): LcsVariable {
        return LcsVariable(n / lcsCoeff, lcsCoeff)
    }

    /** Creates a variable as a fraction of the initial screen width
     */
    fun ofWidth(times: Float): LcsVariable {
        return LcsVariable(initialWidth / lcsCoeff * times, lcsCoeff)
    }

    /** Creates a variable as a fraction of the initial screen height
     */
    fun ofHeight(times: Float): LcsVariable {
        return LcsVariable(initialHeight / lcsCoeff * times, lcsCoeff)
    }

    /** Returns a 0 valued LCS variable
     *
     */
    fun ofZero(): LcsVariable {
        return LcsVariable(0f, lcsCoeff)
    }

    fun ofX(): LcsVariable {
        return LcsVariable(Gdx.input.x.toFloat() / Gdx.graphics.width * initialWidth / lcsCoeff, lcsCoeff)
    }

    fun ofY(): LcsVariable {
        return LcsVariable((1 - Gdx.input.y.toFloat() / Gdx.graphics.height) * initialHeight / lcsCoeff, lcsCoeff)
    }


}