/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.temporizador.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.example.temporizador.presentation.theme.TemporizadorTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val vibrator = ContextCompat.getSystemService( baseContext , Vibrator::class.java) as Vibrator
            val handler = Handler(Looper.getMainLooper())

            var estado: MutableState<Int> = remember { mutableStateOf(0) }

            LaunchedEffect(key1 = true,) {
                GlobalScope.launch(Dispatchers.Default)
                {
                    while (true) {
                        if (estado.value > 0) {
/*                            val vibrationEffect = VibrationEffect.createOneShot(
                                100,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                            vibrator.vibrate(vibrationEffect)   */
                            val currentTime = LocalDateTime.now()
                            Log.d("MARCELO", "* * * VIBRATE   Hora $currentTime")
                            delay(estado.value * 1000L)
                        }
                    }
                }
            }
            // Para utilizar como Thread
            // val vibrationThread = VibrationThread(LocalContext.current)
            // vibrationThread.start()
            TemporizadorTheme {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(onClick = { estado.value++ }) {
                        Text(text = "+1")
                    }
                    Button(onClick = { estado.value-- }) {
                        if (estado.value < 0 )
                        {
                            estado.value = 0
                        }
                        Text(text = "-1")
                    }
                    Text(text = estado.value.toString())
                }
            }
        }
    }
}

/*

class VibrationThread(
    private val appContext : Context
): Thread() {
    private val vibrator = ContextCompat.getSystemService(appContext, Vibrator::class.java) as Vibrator
    private val handler = Handler(Looper.getMainLooper())
    private val vibrationRunnable = object : Runnable {
        override fun run() {
            val vibrationEffect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
            handler.postDelayed(this, 1000)
            val currentTime = LocalDateTime.now()
            Log.d("MARCELO","* * * Buzzz   Hora $currentTime")
        }
    }
    override fun run() {
        handler.post(vibrationRunnable)
    }
} */

