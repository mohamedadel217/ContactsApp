package com.example.local.models

data class LocationLocal(
    val country: String? = null,
    val city: String? = null,
    val street: StreetLocal? = null,
    val timezone: TimezoneLocal? = null,
    val postcode: String? = null,
    val coordinates: CoordinatesLocal? = null,
    val state: String? = null
)