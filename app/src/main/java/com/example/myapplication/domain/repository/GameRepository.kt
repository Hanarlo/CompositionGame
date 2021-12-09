package com.example.myapplication.domain.repository

import com.example.myapplication.domain.entity.GameSettings
import com.example.myapplication.domain.entity.Question
import com.example.myapplication.domain.entity.level

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question
    fun getGameSettings(level: level): GameSettings
}