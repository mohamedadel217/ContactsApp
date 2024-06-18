package com.example.remote.mapper

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
import com.example.remote.models.ContactsItem
import com.example.remote.models.Coordinates
import com.example.remote.models.Dob
import com.example.remote.models.Id
import com.example.remote.models.Location
import com.example.remote.models.Login
import com.example.remote.models.Name
import com.example.remote.models.Picture
import com.example.remote.models.Registered
import com.example.remote.models.Street
import com.example.remote.models.Timezone
import javax.inject.Inject

class ContactsNetworkDataMapper @Inject constructor() :
    Mapper<ContactsItem, ContactsItemDto> {
    override fun from(i: ContactsItem?): ContactsItemDto {
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
            isFavorite = false
        )
    }

    override fun to(o: ContactsItemDto?): ContactsItem {
        return ContactsItem(
            nat = o?.nat,
            gender = o?.gender,
            phone = o?.phone,
            dob = Dob(date = o?.dob?.date, age = o?.dob?.age),
            name = Name(first = o?.name?.first.orEmpty(), last = o?.name?.last.orEmpty(), title = o?.name?.title.orEmpty()),
            registered = Registered(date = o?.registered?.date, age = o?.registered?.age),
            location = Location(
                country = o?.location?.country.orEmpty(),
                city = o?.location?.city,
                state = o?.location?.state,
                postcode = o?.location?.postcode,
                street = Street(
                    number = o?.location?.street?.number,
                    name = o?.location?.street?.name
                ),
                timezone = Timezone(
                    offset = o?.location?.timezone?.offset,
                    description = o?.location?.timezone?.description
                ),
                coordinates = Coordinates(
                    latitude = o?.location?.coordinates?.latitude,
                    longitude = o?.location?.coordinates?.longitude
                )
            ),
            id = Id(name = o?.id?.name, value = o?.id?.value),
            login = Login(
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
            picture = Picture(
                thumbnail = o?.picture?.thumbnail.orEmpty(),
                large = o?.picture?.large.orEmpty(),
                medium = o?.picture?.medium.orEmpty()
            ),
        )
    }
}