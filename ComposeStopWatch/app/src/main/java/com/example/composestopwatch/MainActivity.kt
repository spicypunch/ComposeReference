package com.example.composestopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composestopwatch.ui.theme.ComposeStopWatchTheme
import java.util.Timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainViewModel>()
            val sec = viewModel.sec.value
            val milli = viewModel.milli.value
            val isRunning = viewModel.isRunning.value
            val lapTimes = viewModel.lapTimes.value

            MainScreen(
                sec = sec,
                milli = milli,
                isRunning = isRunning,
                lapTimes = lapTimes,
                onReset = { viewModel.reset() },
                onToggle = { running ->
                    if (running) {
                        viewModel.pause()
                    } else {
                        viewModel.start()
                    }
                },
                onLapTime = { viewModel.recordLapTime() }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    sec: Int,
    milli: Int,
    isRunning: Boolean,
    lapTimes: List<String>,
    onReset: () -> Unit,
    onToggle: (Boolean) -> Unit,
    onLapTime: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "StopWatch") })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "$sec", fontSize = 100.sp)
                Text(text = "$milli")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                lapTimes.forEach { lapTime ->
                    Text(text = lapTime)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = { onReset() },
                    Modifier.background(Color.Red)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_refresh_24),
                        contentDescription = "reset"
                    )
                }

                FloatingActionButton(
                    onClick = { onToggle(isRunning) },
                    Modifier.background(Color.Green)
                ) {
                    Image(
                        painter = painterResource(
                            id =
                            if (isRunning) R.drawable.baseline_pause_24
                            else R.drawable.baseline_play_arrow_24
                        ),
                        contentDescription = "start/pause"
                    )
                }
                Button(onClick = { onLapTime() }) {
                    Text(text = "랩 타임")
                }
            }
        }
    }
}

class MainViewModel : ViewModel() {
    private var time = 0
    private var timerTask: Timer? = null

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    private val _sec = mutableStateOf(0)
    val sec: State<Int> = _sec

    private val _milli = mutableStateOf(0)
    val milli: State<Int> = _milli

    private val _lapTimes = mutableStateOf(mutableListOf<String>())
    val lapTimes: State<List<String>> = _lapTimes

    private var lap = 1

    fun start() {
        _isRunning.value = true

        timerTask = kotlin.concurrent.timer(period = 10) {
            time++
            _sec.value = time / 100
            _milli.value = time % 100
        }
    }

    fun pause() {
        _isRunning.value = false
        timerTask?.cancel()
    }

    fun reset() {
        timerTask?.cancel()

        time = 0
        _isRunning.value = false
        _sec.value = 0
        _milli.value = 0

        _lapTimes.value.clear()
        lap = 1
    }

    fun recordLapTime() {
        _lapTimes.value.add(0, "$lap Lap : ${sec.value}.${milli.value}")
        lap++
    }
}