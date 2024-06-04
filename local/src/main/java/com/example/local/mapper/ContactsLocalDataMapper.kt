package com.example.local.mapper

import com.example.common.Mapper
import com.example.data.model.ContactsItemDto
import com.example.data.model.CoordinatesDto
import com.example.data.model.DobDto
import com.example.data.model.IdDto
import com.example.data.model.LocationDto
import com.example.data.model.LoginDto
import com.example.data.model.NameDto
import com.example.data.model.PictureDto
import com.example.data.model.RegisteredDto
import com.example.data.model.StreetDto
import com.example.data.model.TimezoneDto
import com.example.local.models.ContactsItemLocal
import javax.inject.Inject

class ContactsLocalDataMapper @Inject constructor() :
    Mapper<ContactsItemLocal, ContactsItemDto> {

    override fun from(i: ContactsItemLocal?): ContactsItemDto {
        return ContactsItemDto(
            nat = i?.nat,
            gender = i?.gender,
            phone = i?.phone,
            dob = DobDto(date = i?.dob?.date, age = i?.dob?.age),
            name = NameDto(first = i?.name?.first, last = i?.name?.last, title = i?.name?.title),
            registered = RegisteredDto(date = i?.registered?.date, age = i?.registered?.age),
            location = LocationDto(
                country = i?.location?.country,
                city = i?.location?.city,
                state = i?.location?.state,
                postcode = i?.location?.postcode,
                street = StreetDto(
                    number = i?.location?.street?.number,
                    name = i?.location?.street?.name
                ),
                timezone = TimezoneDto(
                    offset = i?.location?.timezone?.offset,
                    description = i?.location?.timezone?.description
                ),
                coordinates = CoordinatesDto(
                    latitude = i?.location?.coordinates?.latitude,
                    longitude = i?.location?.coordinates?.longitude
                )
            ),
            id = IdDto(name = i?.id?.name, value = i?.id?.value),
            login = LoginDto(
                sha1 = i?.login?.sha1,
                password = i?.login?.password,
                salt = i?.login?.salt,
                sha256 = i?.login?.sha256,
                uuid = i?.login?.uuid,
                username = i?.login?.username,
                md5 = i?.login?.md5
            ),
            cell = i?.cell,
            email = i?.email,
            picture = PictureDto(
                thumbnail = i?.picture?.thumbnail,
                large = i?.picture?.large,
                medium = i?.picture?.medium
            ),
        )
    }

    override fun to(o: ContactsItemDto?): ContactsItemLocal {
        return ContactsItemLocal(tableId = 0)
    }
}