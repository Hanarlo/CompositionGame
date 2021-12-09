package com.example.myapplication.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.data.GameRepositoryImpl
import com.example.myapplication.domain.entity.GameResult
import com.example.myapplication.domain.entity.GameSettings
import com.example.myapplication.domain.entity.Question
import com.example.myapplication.domain.entity.level
import com.example.myapplication.domain.useCases.GenerateQuestionUseCase
import com.example.myapplication.domain.useCases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var gameSettings: GameSettings
    private lateinit var level: level

    private val context = application

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _enoughProcentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughProcentOfRightAnswers: LiveData<Boolean>
        get() = _enoughProcentOfRightAnswers

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var timer: CountDownTimer? = null

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _minpercent = MutableLiveData<Int>()
    val minpercent: LiveData<Int>
        get() = _minpercent


    private var countOfRightAnswers = 0
    private var countOfQuestion = 0

    private val repository = GameRepositoryImpl
    private val generatedQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    fun startGame(level: level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updatePorgress()
    }

    private fun getGameSettings(level: level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        this._minpercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun updatePorgress() {
        val percent = calculateProgressProcent()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughProcentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculateProgressProcent(): Int {
        if (countOfQuestion == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }
            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generatedQuestionUseCase(gameSettings.maxSumValue)
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updatePorgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestion++
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true && enoughProcentOfRightAnswers.value == true,
            countOfRightAnswers, countOfQuestion, gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val SECONDS_IN_MINUTES = 60
        private const val MILLIS_IN_SECONDS = 1000L
    }
}