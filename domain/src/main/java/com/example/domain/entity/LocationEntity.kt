package com.example.domain.entity

data class LocationEntity(
    val country: String? = null,
    val city: String? = null,
    val street: StreetEntity? = null,
    val postcode: String? = null,
    val state: String? = null,
    val timezone: TimezoneEntity? = null,
    val coordinates: CoordinatesEntity? = null,
)