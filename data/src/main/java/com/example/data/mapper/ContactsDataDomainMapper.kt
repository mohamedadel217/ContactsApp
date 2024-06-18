package com.example.data.mapper

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
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.entity.CoordinatesEntity
import com.example.domain.entity.DobEntity
import com.example.domain.entity.IdEntity
import com.example.domain.entity.LocationEntity
import com.example.domain.entity.LoginEntity
import com.example.domain.entity.NameEntity
import com.example.domain.entity.PictureEntity
import com.example.domain.entity.RegisteredEntity
import com.example.domain.entity.StreetEntity
import com.example.domain.entity.TimezoneEntity
import javax.inject.Inject

class ContactsDataDomainMapper @Inject constructor() :
    Mapper<ContactsItemDto, ContactsItemEntity> {

    override fun from(i: ContactsItemDto?): ContactsItemEntity {
        return ContactsItemEntity(
            nat = i?.nat,
            gender = i?.gender,
            phone = i?.phone,
            dob = DobEntity(date = i?.dob?.date, age = i?.dob?.age),
            name = NameEntity(first = i?.name?.first, last = i?.name?.last, title = i?.name?.title),
            registered = RegisteredEntity(date = i?.registered?.date, age = i?.registered?.age),
            location = LocationEntity(
                country = i?.location?.country,
                city = i?.location?.city,
                state = i?.location?.state,
                postcode = i?.location?.postcode,
                street = StreetEntity(
                    number = i?.location?.street?.number,
                    name = i?.location?.street?.name
                ),
                timezone = TimezoneEntity(
                    offset = i?.location?.timezone?.offset,
                    description = i?.location?.timezone?.description
                ),
                coordinates = CoordinatesEntity(
                    latitude = i?.location?.coordinates?.latitude,
                    longitude = i?.location?.coordinates?.longitude
                )
            ),
            id = IdEntity(name = i?.id?.name, value = i?.id?.value),
            login = LoginEntity(
                sha1 = i?.login?.sha1,
                password = i?.login?.password,
                salt = i?.login?.salt,
                sha256 = i?.login?.sha256,
                uuid = i?.login?.uuid.orEmpty(),
                username = i?.login?.username,
                md5 = i?.login?.md5
            ),
            cell = i?.cell,
            email = i?.email,
            picture = PictureEntity(
                thumbnail = i?.picture?.thumbnail,
                large = i?.picture?.large,
                medium = i?.picture?.medium
            ),
            isFavorite = i?.isFavorite
        )
    }

    override fun to(o: ContactsItemEntity?): ContactsItemDto {
        return ContactsItemDto(
            nat = o?.nat,
            gender = o?.gender,
            phone = o?.phone,
            dob = DobDto(date = o?.dob?.date, age = o?.dob?.age),
            name = NameDto(first = o?.name?.first, last = o?.name?.last, title = o?.name?.title),
            registered = RegisteredDto(date = o?.registered?.date, age = o?.registered?.age),
            location = LocationDto(
                country = o?.location?.country,
                city = o?.location?.city,
                state = o?.location?.state,
                postcode = o?.location?.postcode,
                street = StreetDto(
                    number = o?.location?.street?.number,
                    name = o?.location?.street?.name
                ),
                timezone = TimezoneDto(
                    offset = o?.location?.timezone?.offset,
                    description = o?.location?.timezone?.description
                ),
                coordinates = CoordinatesDto(
                    latitude = o?.location?.coordinates?.latitude,
                    longitude = o?.location?.coordinates?.longitude
                )
            ),
            id = IdDto(name = o?.id?.name, value = o?.id?.value),
            login = LoginDto(
                sha1 = o?.login?.sha1,
                password = o?.login?.password,
                salt = o?.login?.salt,
                sha256 = o?.login?.sha256,
                uuid = o?.login?.uuid,
                username = o?.login?.username,
                md5 = o?.login?.md5
            ),
            cell = o?.cell,
            email = o?.email,
            picture = PictureDto(
                thumbnail = o?.picture?.thumbnail,
                large = o?.picture?.large,
                medium = o?.picture?.medium
            ),
            isFavorite = o?.isFavorite
        )
    }
}