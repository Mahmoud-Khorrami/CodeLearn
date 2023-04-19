package com.mahapp1397.codelearn1.viewModel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class F1RegisterViewModel @Inject constructor(): ViewModel()
{
    var time = MutableLiveData<Long>()

    private val timer = object : CountDownTimer(21000, 1000)
    {
        override fun onTick(p0: Long)
        {
           time.value = p0
        }
        override fun onFinish()
        {
        }
    }

    fun startTimer() = timer.start()!!
}