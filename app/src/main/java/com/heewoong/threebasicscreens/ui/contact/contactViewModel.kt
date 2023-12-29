package com.heewoong.threebasicscreens.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class contactViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is contact Fragment"
    }
    val text: LiveData<String> = _text

}