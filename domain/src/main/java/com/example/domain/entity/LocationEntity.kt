package com.example.domain.entity

data class LocationEntity(
    val country: String? = null,
    val city: String? = null,
    val street: StreetEntity? = null,
    val postcode: Int? = null,
    val state: String? = null,
    val timezone: TimezoneEntity? = null,
    val coordinates: CoordinatesEntity? = null,
)