package com.example.myapplication.data

import com.example.myapplication.domain.entity.GameSettings
import com.example.myapplication.domain.entity.Question
import com.example.myapplication.domain.entity.level
import com.example.myapplication.domain.repository.GameRepository
import java.lang.Integer.max
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue+1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum )
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer+ countOfOptions)
        while (options.size < countOfOptions){
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: level): GameSettings {

        return when(level){
            com.example.myapplication.domain.entity.level.TEST -> {GameSettings(
                30,
                2,
                1,
                10
            )}
            com.example.myapplication.domain.entity.level.EASY ->{GameSettings(
                10,
                10,
                50,
                180
            )}
            com.example.myapplication.domain.entity.level.NORMAL ->{GameSettings(
                25,
                20,
                75,
                150
            )}
            com.example.myapplication.domain.entity.level.HARD -> {GameSettings(
                50,
                30,
                90,
                120
            )}
        }
    }
}