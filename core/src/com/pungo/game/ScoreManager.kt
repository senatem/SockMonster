package com.pungo.game

import com.badlogic.gdx.Gdx
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ScoreManager {
    val scores = mutableListOf<Pair<String, Int>>()

    fun newScore(name: String, score: Int) {
        scores.add(Pair(name, score))
    }

    fun saveScores(path: String) {
        val scoreFile = Gdx.files.local(path)
        scoreFile.writeString(Json.encodeToString(scores), false)
    }

    fun loadScores(path: String) {
        try {
            val scoreFile = Gdx.files.local(path)
            scores.clear()
            scores.addAll(Json.decodeFromString<List<Pair<String, Int>>>(scoreFile.readString()))
        } catch (e: Exception) {

        }
    }

    fun listScores() : List<Pair<String, Int>>{
        return scores.sortedByDescending { (_,score) -> score }
    }

    fun clearScores() {
        scores.clear()
    }
}