package com.heewoong.threebasicscreens.ui.free

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class freeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is free Fragment"
    }
    val text: LiveData<String> = _text
}