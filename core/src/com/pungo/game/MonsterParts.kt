package com.pungo.game

import com.pungo.modules.basic.geometry.ConvexPolygon
import com.pungo.modules.basic.geometry.Point
import com.pungo.modules.lcsModule.GetLcs

enum class MonsterParts {
    RightArm{
        val socks = mutableListOf<String>("L_1")
    },
    LeftArm{
        val socks = mutableListOf<String>("L_2")
    },
    RightLeg{
        val socks = mutableListOf<String>("L_3")
    },
    LeftLeg{
        val socks = mutableListOf<String>("M_1")
    },
    Torso{
        val socks = mutableListOf<String>("M_2")
    }
}