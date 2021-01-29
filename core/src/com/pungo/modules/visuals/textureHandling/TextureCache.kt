package com.pungo.modules.visuals.textureHandling

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.pungo.modules.visuals.subTexture.ScalingType
import com.pungo.modules.visuals.subTexture.SubTexture


object TextureCache {
    private var textureList = mutableMapOf<FileHandle, Texture>()
    private var textureAtlasList = mutableMapOf<FileHandle, TextureAtlas>()
    private var jsonAtlasList = mutableMapOf<FileHandle, AnimateJson>()
    private var pixmapTextures = mutableListOf<Texture>() //this is for disposing

    fun addToPixmapTextures(t: Texture) {
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        pixmapTextures.add(t)
    }


    fun jsonOpener(path: FileHandle, visualPath: FileHandle? = null, func: (String) -> Boolean = { true }): MultipleTexture {
        val at: AnimateJson
        if (jsonAtlasList.containsKey(path)) {
            at = jsonAtlasList[path]!!
        } else {
            at = AnimateJson(path, visualPath)
            jsonAtlasList[path] = at
        }
        return MultipleTexture(at.getSubTextures(func))
    }


    fun atlasOpener(path: FileHandle, region: String? = null): MultipleTexture {
        openAtlasTexture(path).also {
            val that = mutableListOf<SubTexture>()
            (if (region == null) {
                it.createSprites()
            } else {
                it.createSprites(region)
            }).forEach { it2 ->
                that.add(SubTexture(it2))
            }
            return MultipleTexture(that)
        }
    }


    private fun openAtlasTexture(path: FileHandle): TextureAtlas {
        return if (textureAtlasList.containsKey(path)) {
            textureAtlasList[path] as TextureAtlas
        } else {
            textureAtlasList[path] = TextureAtlas(path).also {
                it.textures.forEach {
                    it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
                }
            }
            textureAtlasList[path] as TextureAtlas
        }
    }


    fun openTexture(path: FileHandle): Texture {
        return if (textureList.containsKey(path)) {
            textureList[path] as Texture
        } else {

            textureList[path] = Texture(path).also {
                it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
            }
            textureList[path] as Texture
        }
    }

    fun subTextureFromPath(path: FileHandle, colour: Color = Color.WHITE, scalingType: ScalingType = ScalingType.FIT_ELEMENT, scaleFactor: Float = 1f): SubTexture {
        return SubTexture(openTexture(path).also {
            it.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        }).also {
            it.color = colour
            it.setScaling(scalingType, scaleFactor)
        }
    }


    fun dispose() {
        textureList.forEach {
            it.value.dispose()
        }
        textureAtlasList.forEach {
            it.value.textures.forEach { it2 ->
                it2.dispose()
            }
        }
        pixmapTextures.forEach {
            it.dispose()
        }
        jsonAtlasList.forEach {
            it.value.dispose()

        }
    }
}