package com.example.domain.utils

import com.example.common.PagingModel
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.entity.DobEntity
import com.example.domain.entity.LocationEntity
import com.example.domain.entity.NameEntity
import com.example.domain.entity.PictureEntity


class TestDataGenerator {

    companion object {
        fun generateContacts(): PagingModel<List<ContactsItemEntity>> {
            val list = mutableListOf<ContactsItemEntity>().apply {
                add(
                    ContactsItemEntity(
                        nat = "",
                        gender = "",
                        phone = "123",
                        dob = DobEntity(),
                        name = NameEntity(
                            first = "testFirstName1",
                            last = "testLastName1",
                            title = "MS"
                        ),
                        location = LocationEntity(country = "NL"),
                        picture = PictureEntity(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemEntity(
                        nat = "",
                        gender = "",
                        phone = "234",
                        dob = DobEntity(),
                        name = NameEntity(
                            first = "testFirstName2",
                            last = "testLastName2",
                            title = "MS"
                        ),
                        location = LocationEntity(country = "PT"),
                        picture = PictureEntity(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemEntity(
                        nat = "",
                        gender = "",
                        phone = "345",
                        dob = DobEntity(),
                        name = NameEntity(
                            first = "testFirstName3",
                            last = "testLastName3",
                            title = "MS"
                        ),
                        location = LocationEntity(country = "ES"),
                        picture = PictureEntity(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemEntity(
                        nat = "",
                        gender = "",
                        phone = "456",
                        dob = DobEntity(),
                        name = NameEntity(
                            first = "testFirstName4",
                            last = "testLastName4",
                            title = "MS"
                        ),
                        location = LocationEntity(country = "GR"),
                        picture = PictureEntity(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemEntity(
                        nat = "",
                        gender = "",
                        phone = "567",
                        dob = DobEntity(),
                        name = NameEntity(
                            first = "testFirstName5",
                            last = "testLastName5",
                            title = "MS"
                        ),
                        location = LocationEntity(country = "FR"),
                        picture = PictureEntity(thumbnail = "", medium = "", large = ""),
                    )
                )
                add(
                    ContactsItemEntity(
                        nat = "",
                        gender = "",
                        phone = "678",
                        dob = DobEntity(),
                        name = NameEntity(
                            first = "testFirstName6",
                            last = "testLastName6",
                            title = "MS"
                        ),
                        location = LocationEntity(country = "PL"),
                        picture = PictureEntity(thumbnail = "", medium = "", large = ""),
                    )
                )
            }
            return PagingModel(list, total = 6, currentPage = 1)
        }
    }

}