package com.example

import android.app.Application
import android.media.SoundPool
import androidx.lifecycle.AndroidViewModel
import com.example.composexylophone.R

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val soundPool = SoundPool.Builder().setMaxStreams(8).build()
    private val sounds = listOf(
        soundPool.load(application.applicationContext, R.raw.do1, 1),
        soundPool.load(application.applicationContext, R.raw.re, 1),
        soundPool.load(application.applicationContext, R.raw.mi, 1),
        soundPool.load(application.applicationContext, R.raw.fa, 1),
        soundPool.load(application.applicationContext, R.raw.sol, 1),
        soundPool.load(application.applicationContext, R.raw.la, 1),
        soundPool.load(application.applicationContext, R.raw.si, 1),
        soundPool.load(application.applicationContext, R.raw.do2, 1),
    )

    fun playSound(index: Int) {
        soundPool.play(sounds[index], 1f, 1f, 0, 0, 1f)
    }

    override fun onCleared() {
        soundPool.release()
        super.onCleared()
    }
}