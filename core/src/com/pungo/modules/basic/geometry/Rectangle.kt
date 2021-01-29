package com.pungo.modules.basic.geometry

class Rectangle : ConvexPolygon {
    var top: Float
    var bottom: Float
    var left: Float
    var right: Float
    var width: Float
    var height: Float

    constructor(bottomLeft: Point, topRight: Point) : super() {
        points = mutableListOf(bottomLeft, Point(topRight.x, bottomLeft.y), topRight, Point(bottomLeft.x, topRight.y))
        top = topRight.y
        bottom = bottomLeft.y
        right = topRight.x
        left = bottomLeft.x
        width = right - left
        height = top - bottom
    }

    constructor(w1: Float, w2: Float, h1: Float, h2: Float) {
        this.top = h1.coerceAtLeast(h2)
        this.bottom = h1.coerceAtMost(h2)
        this.right = w1.coerceAtLeast(w2)
        this.left = w1.coerceAtMost(w2)
        points = mutableListOf(Point(left, bottom), Point(right, bottom), Point(right, top), Point(left, top))
        width = right - left
        height = top - bottom
    }

    operator fun plus(other: Rectangle): Rectangle {
        val t = (top).coerceAtLeast(other.top)
        val b = (bottom).coerceAtMost(other.bottom)
        val l = (left).coerceAtMost(other.left)
        val r = (right).coerceAtLeast(other.right)
        return Rectangle(l, r, b, t)
    }

    /** This function takes itself as unit rectangle and input as ratios for it, and returns the adjusted rectangle
     * ex: this = (left: 0,bottom: 0,right: 2,top: 1), other = (0.25,0.25,0.75,0.75) ->  (0.5,0.25,1.5,0.75)
     */
    fun getSubRectangle(other: Rectangle): Rectangle {
        val l = left + width * other.left
        val r = left + width * other.right
        val b = bottom + height * other.bottom
        val t = bottom + height * other.top
        return Rectangle(l, r, b, t)

    }

    fun dataString(): String {
        return "left: $left right: $right bottom: $bottom top: $top"
    }

    fun flipX(n: Float): Rectangle {
        return Rectangle(2 * n - left, 2 * n - right, bottom, top)
    }

    fun copy(): Rectangle {
        return Rectangle(this.left, this.right, this.bottom, this.top)
    }

    fun move(dx: Float = 0f, dy: Float = 0f) {
        top += dy
        bottom += dy
        right += dx
        left += dx
        points = points.map { Point(it.x + dx, it.y + dy) }.toMutableList()
    }

    fun moved(dx: Float = 0f, dy: Float = 0f): Rectangle {
        copy().also {
            it.move(dx, dy)
            return it
        }

    }

    /** This function returns a rectangle in between this and the other according to the coefficient
     * if coeff is 0 is here 1 is there
     */
    fun averageRect(other: Rectangle, coeff: Float = 0.5f): Rectangle {
        return Rectangle(this.left * (1 - coeff) + other.left * (coeff), this.right * (1 - coeff) + other.right * (coeff),
                this.bottom * (1 - coeff) + other.bottom * (coeff), this.top * (1 - coeff) + other.top * (coeff))
    }

    /** This bad boy returns a rectangle, to a rectangle at a different origin coordinates pair
     * within sets the limits in which the origin will be swapped
     * This does not move the origin itself, which is a different functionality
     * ex: r.switchOrigin(from: Origin.TOP_LEFT,to: BOTTOM_LEFT) returns the coordinates of the same rectangle defined by top left as bottom left
     */
    fun switchOrigin(from: Origin, to: Origin, within: Rectangle = FastGeometry.unitSquare()): Rectangle {
        return if ((from == Origin.TOPLEFT) && (to == Origin.BOTTOMLEFT)) {
            Rectangle(this.left, this.right, within.height - this.bottom, within.height - this.top)
        } else if ((from == Origin.BOTTOMLEFT) && (to == Origin.TOPLEFT)) {
            Rectangle(this.left, this.right, within.height - this.bottom, within.height - this.top)
        } else {
            copy()
        }
    }


}