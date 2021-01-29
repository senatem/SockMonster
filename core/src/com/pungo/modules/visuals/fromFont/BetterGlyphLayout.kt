package com.pungo.modules.visuals.fromFont

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout

/** We write this, as libgdx GlyphLayout is a nightmare to work with
 * We hope to improve it by, making some arguments optional
 * holding settings as variables
 * Adding a variable that holds tail lengths
 */
class BetterGlyphLayout : GlyphLayout {
    var font: BitmapFont? = null
        private set
    var text: String? = null
        set(value) {
            field = value
            if ((font != null) && (value != null)) {
                setText(font, value)
            }
        }
    var colour = Color.WHITE
        private set
    var hAlign = TextAlignment.CENTRE
        private set
    var wrap = true
        private set
    var targetWidth = 0f
        private set
    var punto = 0
        private set
    var path = ""
        private set

    constructor() : super() //don't use if possible

    /** This constructor is a bit sketchy, but i think it'd work
     */
    constructor(font: BitmapFont, text: String? = null, colour: Color = Color.WHITE, targetWidth: Float = 0f, hAlign: TextAlignment = TextAlignment.CENTRE, wrap: Boolean = true) : super() {
        this.font = font
        this.text = text
        this.colour = colour
        this.hAlign = hAlign
        this.wrap = wrap
        this.targetWidth = targetWidth
        this.punto = font.data.name.split("-").last().toInt()
        setTextProperties()
    }

    constructor(path: String, punto: Int, text: String? = null, colour: Color = Color.WHITE, targetWidth: Float = 0f, hAlign: TextAlignment = TextAlignment.CENTRE, wrap: Boolean = true) : super() {
        this.punto = punto
        this.path = path
        this.text = text
        this.colour = colour
        this.hAlign = hAlign
        this.wrap = wrap
        this.targetWidth = targetWidth
        this.font = FontGenerator.getFont(path, punto)
        this.text = text
        if (text != null) {
            setTextProperties()
        }
    }


    private fun textAlignmentTohAlign(t: TextAlignment): Int {
        return when (t) {
            TextAlignment.TOP_LEFT, TextAlignment.LEFT, TextAlignment.BOTTOM_LEFT -> -1
            TextAlignment.TOP_CENTRE, TextAlignment.CENTRE, TextAlignment.BOTTOM_CENTRE -> 1
            TextAlignment.BOTTOM_RIGHT, TextAlignment.TOP_RIGHT, TextAlignment.RIGHT -> 0
        }
    }

    private fun changeTextAlignment(alignment: TextAlignment) {
        setTextProperties(hAlign = alignment)
    }

    /** We override setText because fuck setText
     */
    override fun setText(font: BitmapFont?, str: CharSequence?) {
        this.font = font
        if (str.toString() != text) {
            this.text = str.toString()
        }
        super.setText(font, str)
    }

    /** Sets the other properties
     */
    fun setTextProperties(colour: Color = this.colour, targetWidth: Float = this.targetWidth, hAlign: TextAlignment = this.hAlign, wrap: Boolean = this.wrap) {
        setText(font, text, colour, targetWidth, textAlignmentTohAlign(hAlign), wrap)
    }

    fun setFontProperties(punto: Int = this.punto, path: String = this.path) {
        if ((punto != this.punto) || (path != this.path)) {
            this.punto = punto
            this.path = path
            font = FontGenerator.getFont(path, punto)
            setText(font, text)
        }
    }
}