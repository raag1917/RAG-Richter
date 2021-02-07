package com.raag.ragrichter.repository

import androidx.lifecycle.LiveData
import com.raag.ragrichter.data.Earthquake
import com.raag.ragrichter.database.EqDatabase
import com.raag.ragrichter.responses.TResponses
import com.raag.ragrichter.webservices.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repo (private val eqDatabase: EqDatabase) {

    suspend fun fetchTerremotoData(sortByMagnitude: Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            val terremotosList: TResponses = service.getTerremotosData()
            val eq = parseResult(terremotosList)

            eqDatabase.eqDao.insertAll(eq)
            fetchTerremotDB(sortByMagnitude)
        }
    }

    suspend fun fetchTerremotDB(sortByMagnitude: Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            if (sortByMagnitude) {
                eqDatabase.eqDao.getEartquakeByMagnitude()
            } else {
                eqDatabase.eqDao.getEartquake()
            }
        }
    }

    private fun parseResult(tResponse: TResponses): MutableList<Earthquake>{
        val tList = mutableListOf<Earthquake>()
        val featuresList = tResponse.features

        for (features in featuresList){
            val properties = features.properties

            val id = features.id
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = features.geometry
            val latitude = geometry.latitude
            val longitude = geometry.longitude

            tList.add(Earthquake(id,place,magnitude,time,longitude, latitude))
        }
        return tList
    }
}