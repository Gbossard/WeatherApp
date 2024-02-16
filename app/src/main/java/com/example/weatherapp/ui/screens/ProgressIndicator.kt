package com.example.weatherapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.weatherapp.data.DataSource
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

class ProgressIndicator(
    val maxProgress: Int = 100,
    private val progressDelayMillis: Long = 600L,
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0
) {
    var currentProgress by mutableStateOf(initialProgress)
        private set

    var currentText by mutableIntStateOf(DataSource.messages[0])
        private set

    fun reset() {
        currentProgress = 0
    }

    suspend fun loading() {
        try {
            while (currentProgress < maxProgress) {
                delay(progressDelayMillis)
                currentProgress += progressIncrement
            }
        } catch (e: CancellationException) {
            Log.e("Loader", "${e.message}")
            throw e
        }
    }

    suspend fun updateText() {
        try {
            while (currentProgress < maxProgress) {
                for (message in DataSource.messages) {
                    currentText = message
                    delay(6000)
                }
            }
        } catch (e: CancellationException) {
            throw e
        }
    }
}

val ProgressIndicator.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()
