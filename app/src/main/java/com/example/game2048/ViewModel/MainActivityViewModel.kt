package com.example.game2048.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class MainActivityViewModel : ViewModel() {

    var gestureDirection = MutableLiveData<Int>()
}