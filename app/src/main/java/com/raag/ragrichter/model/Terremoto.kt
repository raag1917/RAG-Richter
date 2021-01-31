package com.raag.ragrichter.model

data class Terremoto(
    val id: String,
    val place: String,
    val magnitude: Double,
    val time: Long,
    val longitude: Double,
    val latitude: Double,

)
