package com.raag.ragrichter.viewmodel

import android.app.Application
import android.util.Log.e
import androidx.lifecycle.*
import com.raag.ragrichter.data.Earthquake
import com.raag.ragrichter.database.getDatabase
import com.raag.ragrichter.repository.Repo
import com.raag.ragrichter.responses.Responses
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName
class MainViewModel(application: Application, private val sortType: Boolean) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repo = Repo(database)

    private val _status = MutableLiveData<Responses>()
    val status: LiveData<Responses>
    get() = _status

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
    get() = _eqList

    init {
        reloadEarthquakes()

    }

    private fun reloadEarthquakes() {
        viewModelScope.launch {
            try {
                _status.value = Responses.LOADING
                _eqList.value = repo.fetchTerremotoData(sortType)
                _status.value = Responses.DONE
            } catch (e: UnknownHostException) {
                _status.value = Responses.ERROR
                e(TAG, "error de conexión", e)
            }
        }
    }

    fun reloadEarthquakesFromDb(sortByMagnitude: Boolean) {
        viewModelScope.launch {
            _eqList.value = repo.fetchTerremotDB(sortByMagnitude)
        }
    }


}
