package com.example.remote.utils

import com.example.remote.models.ContactsResponse
import com.example.remote.models.Dob
import com.example.remote.models.Location
import com.example.remote.models.Name
import com.example.remote.models.Picture
import com.example.remote.models.ContactsItem

class TestDataGenerator {

    companion object {
        fun generateContacts(): ContactsResponse {
            val list = mutableListOf<ContactsItem>().apply {
                add(
                    ContactsItem(
                        nat = "",
                        gender = "",
                        phone = "123",
                        dob = Dob(),
                        name = Name(first = "testFirstName1", last = "testLastName1", title = "MS"),
                        location = Location(country = "NL"), picture = Picture(thumbnail = "", medium = "", large = "")
                    )
                )
                add(
                    ContactsItem(
                        nat = "",
                        gender = "",
                        phone = "234",
                        dob = Dob(),
                        name = Name(first = "testFirstName2", last = "testLastName2",title = "MS"),
                        location = Location(country = "PT"), picture = Picture(thumbnail = "", medium = "", large = "")
                    )
                )
                add(
                    ContactsItem(
                        nat = "",
                        gender = "",
                        phone = "345",
                        dob = Dob(),
                        name = Name(first = "testFirstName3", last = "testLastName3",title = "MS"),
                        location = Location(country = "ES"), picture = Picture(thumbnail = "", medium = "", large = "")
                    )
                )
                add(
                    ContactsItem(
                        nat = "",
                        gender = "",
                        phone = "456",
                        dob = Dob(),
                        name = Name(first = "testFirstName4", last = "testLastName4",title = "MS"),
                        location = Location(country = "GR"), picture = Picture(thumbnail = "", medium = "", large = "")
                    )
                )
                add(
                    ContactsItem(
                        nat = "",
                        gender = "",
                        phone = "567",
                        dob = Dob(),
                        name = Name(first = "testFirstName5", last = "testLastName5",title = "MS"),
                        location = Location(country = "FR"), picture = Picture(thumbnail = "", medium = "", large = "")
                    )
                )
                add(
                    ContactsItem(
                        nat = "",
                        gender = "",
                        phone = "678",
                        dob = Dob(),
                        name = Name(first = "testFirstName6", last = "testLastName6",title = "MS"),
                        location = Location(country = "PL"), picture = Picture(thumbnail = "", medium = "", large = "")
                    )
                )
            }

            return ContactsResponse(
                list
            )
        }
    }

}