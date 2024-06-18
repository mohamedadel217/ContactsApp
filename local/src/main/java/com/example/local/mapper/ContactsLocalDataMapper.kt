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
import com.example.local.models.CoordinatesLocal
import com.example.local.models.DobLocal
import com.example.local.models.IdLocal
import com.example.local.models.LocationLocal
import com.example.local.models.LoginLocal
import com.example.local.models.NameLocal
import com.example.local.models.PictureLocal
import com.example.local.models.RegisteredLocal
import com.example.local.models.StreetLocal
import com.example.local.models.TimezoneLocal
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
            isFavorite = i?.isFavorite
        )
    }

    override fun to(o: ContactsItemDto?): ContactsItemLocal {
        return ContactsItemLocal(
            tableId = o?.login?.uuid.orEmpty(),
            nat = o?.nat,
            gender = o?.gender,
            phone = o?.phone,
            dob = DobLocal(date = o?.dob?.date, age = o?.dob?.age),
            name = NameLocal(first = o?.name?.first, last = o?.name?.last, title = o?.name?.title),
            registered = RegisteredLocal(date = o?.registered?.date, age = o?.registered?.age),
            location = LocationLocal(
                country = o?.location?.country,
                city = o?.location?.city,
                state = o?.location?.state,
                postcode = o?.location?.postcode,
                street = StreetLocal(
                    number = o?.location?.street?.number,
                    name = o?.location?.street?.name
                ),
                timezone = TimezoneLocal(
                    offset = o?.location?.timezone?.offset,
                    description = o?.location?.timezone?.description
                ),
                coordinates = CoordinatesLocal(
                    latitude = o?.location?.coordinates?.latitude,
                    longitude = o?.location?.coordinates?.longitude
                )
            ),
            id = IdLocal(name = o?.id?.name, value = o?.id?.value),
            login = LoginLocal(
                sha1 = o?.login?.sha1,
                password = o?.login?.password,
                salt = o?.login?.salt,
                sha256 = o?.login?.sha256,
                uuid = o?.login?.uuid.orEmpty(),
                username = o?.login?.username,
                md5 = o?.login?.md5
            ),
            cell = o?.cell,
            email = o?.email,
            picture = PictureLocal(
                thumbnail = o?.picture?.thumbnail,
                large = o?.picture?.large,
                medium = o?.picture?.medium
            ),
            isFavorite = o?.isFavorite ?: false
        )
    }
}