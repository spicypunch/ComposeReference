package com.example.composexylophone

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MainViewModel
import com.example.composexylophone.ui.theme.ComposeXylophoneTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContent {
            ComposeXylophoneTheme {
                Screen(viewModel)
            }
        }
    }
}

@Composable
fun Screen(viewModel: MainViewModel) {
    val keys = listOf(
        Pair("도", Color.Red),
        Pair("레", Color(0XFFFF9800)),
        Pair("미", Color(0xFFFFC107)),
        Pair("파", Color(0xFF8BC34A)),
        Pair("솔", Color(0xFF2196F3)),
        Pair("라", Color(0xFF3F51B5)),
        Pair("시", Color(0xFF673AB7)),
        Pair("도", Color.Red),
    )

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        keys.forEachIndexed { index, key ->
            val padding = (index + 2) * 8
            Keyboard(
                modifier = Modifier
                    .padding(top = padding.dp, bottom = padding.dp)
                    .clickable { viewModel.playSound(index) },
                color = key.second,
                text = key.first
            )
        }
    }
}

@Composable
fun Keyboard(modifier: Modifier, color: Color, text: String) {
    Box(
        modifier = modifier
            .width(50.dp)
            .fillMaxHeight()
            .background(color = color)
    ) {
        Text(
            text = text,
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}