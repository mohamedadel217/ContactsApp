package com.example.data.utils

import com.example.common.PagingModel
import com.example.data.model.ContactsItemDto
import com.example.data.model.DobDto
import com.example.data.model.LocationDto
import com.example.data.model.NameDto
import com.example.data.model.PictureDto


class TestDataGenerator {

    companion object {
        fun generateContacts(): PagingModel<List<ContactsItemDto>> {
            val list = mutableListOf<ContactsItemDto>().apply {
                add(
                    ContactsItemDto(
                        nat = "",
                        gender = "",
                        phone = "123",
                        dob = DobDto(),
                        name = NameDto(
                            first = "testFirstName1",
                            last = "testLastName1",
                            title = "MS"
                        ),
                        location = LocationDto(country = "NL"),
                        picture = PictureDto(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemDto(
                        nat = "",
                        gender = "",
                        phone = "234",
                        dob = DobDto(),
                        name = NameDto(
                            first = "testFirstName2",
                            last = "testLastName2",
                            title = "MS"
                        ),
                        location = LocationDto(country = "PT"),
                        picture = PictureDto(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemDto(
                        nat = "",
                        gender = "",
                        phone = "345",
                        dob = DobDto(),
                        name = NameDto(
                            first = "testFirstName3",
                            last = "testLastName3",
                            title = "MS"
                        ),
                        location = LocationDto(country = "ES"),
                        picture = PictureDto(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemDto(
                        nat = "",
                        gender = "",
                        phone = "456",
                        dob = DobDto(),
                        name = NameDto(
                            first = "testFirstName4",
                            last = "testLastName4",
                            title = "MS"
                        ),
                        location = LocationDto(country = "GR"),
                        picture = PictureDto(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemDto(
                        nat = "",
                        gender = "",
                        phone = "567",
                        dob = DobDto(),
                        name = NameDto(
                            first = "testFirstName5",
                            last = "testLastName5",
                            title = "MS"
                        ),
                        location = LocationDto(country = "FR"),
                        picture = PictureDto(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemDto(
                        nat = "",
                        gender = "",
                        phone = "678",
                        dob = DobDto(),
                        name = NameDto(
                            first = "testFirstName6",
                            last = "testLastName6",
                            title = "MS"
                        ),
                        location = LocationDto(country = "PL"),
                        picture = PictureDto(thumbnail = "", medium = "", large = ""),
                    )
                )
            }
            return PagingModel(list, total = 6, currentPage = 1)
        }
    }

}