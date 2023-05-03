package kau.brave.breakthecycle

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoseDaysApplication : Application() {

    companion object {
        val isSecretMode: MutableState<Boolean> = mutableStateOf(false)
    }
}