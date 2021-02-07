package com.raag.ragrichter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raag.ragrichter.data.Terremoto
import com.raag.ragrichter.repository.Repo
import kotlinx.coroutines.*


class MainViewModel : ViewModel() {
    private var _tList = MutableLiveData<MutableList<Terremoto>>()
    val tList: LiveData<MutableList<Terremoto>>
        get() = _tList

    private val repo = Repo()

    init {
        viewModelScope.launch {
            _tList.value = repo.fetchTerremotoData()
        }

    }


}
