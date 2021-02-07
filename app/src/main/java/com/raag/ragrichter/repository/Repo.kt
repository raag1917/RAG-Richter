package com.raag.ragrichter.repository

import com.raag.ragrichter.data.Terremoto
import com.raag.ragrichter.responses.TResponses
import com.raag.ragrichter.webservices.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repo {
    suspend fun fetchTerremotoData(): MutableList<Terremoto> {
        return withContext(Dispatchers.IO){
            val terremotosList: TResponses = service.getTerremotosData()
            val eq = parseResult(terremotosList)

            eq
        }
    }

    private fun parseResult(tResponse: TResponses): MutableList<Terremoto>{
        val tList = mutableListOf<Terremoto>()
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

            tList.add(Terremoto(id,place,magnitude,time,longitude, latitude))
        }
        return tList
    }
}