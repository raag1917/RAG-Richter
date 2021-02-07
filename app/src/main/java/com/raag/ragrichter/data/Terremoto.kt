package com.raag.ragrichter.data

data class Terremoto(
    val id: String = "",
    val place: String = "",
    val magnitude: Double = 0.0,
    val time: Long = 0,
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,

    )
