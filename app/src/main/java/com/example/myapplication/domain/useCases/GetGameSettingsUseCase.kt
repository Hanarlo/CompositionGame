package com.example.myapplication.domain.useCases

import com.example.myapplication.domain.entity.GameSettings
import com.example.myapplication.domain.entity.Question
import com.example.myapplication.domain.entity.level
import com.example.myapplication.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: level): GameSettings{
        return repository.getGameSettings(level)
    }

    private companion object{
        private val COUNT_OF_OPTIONS = 6
    }
}