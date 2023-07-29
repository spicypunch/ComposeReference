package toss.next.composestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HoseScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoseScreen(viewModel: MainViewModel = viewModel()) {
    val text1: MutableState<String> = remember {
        mutableStateOf("Hello World")
    }
    var text2 by remember {
        mutableStateOf("Hello World")
    }
    val (text3, setText) = remember {
        mutableStateOf("Hello World")
    }

    val text4: State<String> = viewModel.liveData.observeAsState("Hello World")

    Column {
        /**
         * 결국 데이터를 변경하려는 행위
         * 이 행위를 하면 화면은 다시 그려진다.
         */
        Text(text = "Hello World")
        Button(onClick = {}) {
            text1.value = "변경"
            text2 = "변경"
//            text3 = "변경"  불가
            setText("변경")
            viewModel.changeValue("변경")
        }
        TextField(value = text3, onValueChange = setText)
    }
}

class MainViewModel : ViewModel() {
    private val _value = mutableStateOf("Hello World")
    val value: State<String> = _value

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData
    fun changeValue(value: String) {
        _value.value = value
        _liveData.value = value
    }
}