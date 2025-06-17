package com.example.pnp2_newproject

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object TimerManager {
    private var timer: CountDownTimer? = null
    private val timer_Finished = MutableLiveData<Boolean>()
    val timerFinished: LiveData<Boolean> get() = timer_Finished

    fun startTimer(durationInSeconds: Int) {
        timer?.cancel()
        timer_Finished.postValue(false)
        timer = object: CountDownTimer(durationInSeconds * 1000L,1000L){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                timer_Finished.postValue(true)
            }
        }.start()
    }

    fun cancelTimer() {
        timer?.cancel()
        timer_Finished.postValue(false)
    }
}