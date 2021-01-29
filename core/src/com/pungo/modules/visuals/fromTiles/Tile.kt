package com.pungo.modules.visuals.fromTiles

data class Tile(val id: String, var type: String, var row: Float, var column: Float, var z: Int = 1, var visible: Boolean = true)