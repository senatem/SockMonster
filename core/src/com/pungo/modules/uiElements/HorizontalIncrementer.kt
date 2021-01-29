package com.pungo.modules.uiElements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pungo.modules.visuals.fromFont.BlockText

/** As the name suggest this is an incrementer device, applied horizontally
 * it has an incrementer and a large incerementer which allows us to define what large incremental difference means
 */
class HorizontalIncrementer(id: String, fontPath: String, bgColour: Color, textSize: Int, initialNumber: Int, textColour: Color = Color.WHITE, private var largeIncrementer: Int = 10, private var optionsList: List<Any>) : UiElement(id) {
    private lateinit var theValue: Any
    private var theNumber = initialNumber
        private set(value) {
            field = value
            theValue = optionsList[value]
        }
    private var blockText = BlockText("${optionsList[theNumber]}", textSize, textColour, fontPath)

    private var decButton = FastGenerator.genericSetButton("dec", "<", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = { changeNumber(-1) }
        blockText.text = "${optionsList[theNumber]}"
        //blockText.changeText("${optionsList[theNumber]}")
    }
    private var decDecButton = FastGenerator.genericSetButton("decdec", "<<", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = {
            changeNumber(-1 * largeIncrementer)
        }

    }
    private var incButton = FastGenerator.genericSetButton("inc", ">", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = { changeNumber(1) }
        blockText.text = "${optionsList[theNumber]}"
        //blockText.changeText("${optionsList[theNumber]}")
    }
    private var incIncButton = FastGenerator.genericSetButton("incinc", ">>", textSize, bgColour = bgColour, textColour = textColour, fontPath = fontPath).also {
        it.clicked = { changeNumber(1 * largeIncrementer) }
        blockText.text = "${optionsList[theNumber]}"
        //blockText.changeText("${optionsList[theNumber]}")
    }

    private var textImage = PinupImage("text", blockText)
    var numChangeFunc = {}

    init {
        district.splitToPlots("increment", row = 1, col = 5).also {
            it[0].element = decDecButton
            it[1].element = decButton
            it[2].element = textImage
            it[3].element = incButton
            it[4].element = incIncButton
        }
        /*
        divideBlocksToBiased(listOf(1f,1f,2f,1f,1f))
        replaceElement(0,decDecButton)
        replaceElement(1,decButton)
        replaceElement(2,textImage)
        replaceElement(3,incButton)
        replaceElement(4,incIncButton)

         */

    }

    fun setValue(v: Any) {
        val a = optionsList.indexOfFirst {
            it == v
        }
        if (a > -1) {
            theNumber = a
            blockText.text = "${optionsList[theNumber]}"
            //blockText.changeText("${optionsList[theNumber]}")
            numChangeFunc()
        }
    }


    private fun changeNumber(n: Int) {
        val newNum = (theNumber + n).coerceAtLeast(0).coerceAtMost(optionsList.size - 1)
        if (newNum != theNumber) {
            theNumber = newNum
            blockText.text = "${optionsList[theNumber]}"
            numChangeFunc()
        }
    }

    override fun touchHandler(mayTouch: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(batch: SpriteBatch, alpha: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dispose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}