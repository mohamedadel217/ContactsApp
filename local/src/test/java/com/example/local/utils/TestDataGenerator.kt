package com.example.local.utils

import com.example.local.models.ContactsItemLocal
import com.example.local.models.ContactsLocal
import com.example.local.models.DobLocal
import com.example.local.models.LocationLocal
import com.example.local.models.NameLocal
import com.example.local.models.PictureLocal

class TestDataGenerator {

    companion object {
        fun generateContacts(): List<ContactsItemLocal> {
            val list = mutableListOf<ContactsItemLocal>().apply {
                add(
                    ContactsItemLocal(
                        nat = "",
                        gender = "",
                        phone = "123",
                        dob = DobLocal(),
                        name = NameLocal(
                            first = "testFirstName1",
                            last = "testLastName1",
                            title = "MS"
                        ),
                        location = LocationLocal(country = "NL"),
                        picture = PictureLocal(thumbnail = "", medium = "", large = ""),
                        tableId = 0
                    )
                )
                add(
                    ContactsItemLocal(
                        nat = "",
                        gender = "",
                        phone = "234",
                        dob = DobLocal(),
                        name = NameLocal(
                            first = "testFirstName2",
                            last = "testLastName2",
                            title = "MS"
                        ),
                        location = LocationLocal(country = "PT"),
                        picture = PictureLocal(thumbnail = "", medium = "", large = ""),
                        tableId = 1
                    )
                )
                add(
                    ContactsItemLocal(
                        nat = "",
                        gender = "",
                        phone = "345",
                        dob = DobLocal(),
                        name = NameLocal(
                            first = "testFirstName3",
                            last = "testLastName3",
                            title = "MS"
                        ),
                        location = LocationLocal(country = "ES"),
                        picture = PictureLocal(thumbnail = "", medium = "", large = ""),
                        tableId = 2
                    )
                )
                add(
                    ContactsItemLocal(
                        nat = "",
                        gender = "",
                        phone = "456",
                        dob = DobLocal(),
                        name = NameLocal(
                            first = "testFirstName4",
                            last = "testLastName4",
                            title = "MS"
                        ),
                        location = LocationLocal(country = "GR"),
                        picture = PictureLocal(thumbnail = "", medium = "", large = ""),
                        tableId = 3
                    )
                )
                add(
                    ContactsItemLocal(
                        nat = "",
                        gender = "",
                        phone = "567",
                        dob = DobLocal(),
                        name = NameLocal(
                            first = "testFirstName5",
                            last = "testLastName5",
                            title = "MS"
                        ),
                        location = LocationLocal(country = "FR"),
                        picture = PictureLocal(thumbnail = "", medium = "", large = ""),
                        tableId = 4
                    )
                )
                add(
                    ContactsItemLocal(
                        nat = "",
                        gender = "",
                        phone = "678",
                        dob = DobLocal(),
                        name = NameLocal(
                            first = "testFirstName6",
                            last = "testLastName6",
                            title = "MS"
                        ),
                        location = LocationLocal(country = "PL"),
                        picture = PictureLocal(thumbnail = "", medium = "", large = ""),
                        tableId = 5
                    )
                )
            }

            return list

        }
    }

}