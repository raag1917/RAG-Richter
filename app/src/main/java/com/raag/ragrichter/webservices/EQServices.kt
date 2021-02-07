package com.raag.ragrichter.webservices


import com.raag.ragrichter.responses.TResponses
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface EQServices {
    @GET("all_hour.geojson")
    suspend fun getTerremotosData(): TResponses
}


private var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: EQServices = retrofit.create(EQServices::class.java)