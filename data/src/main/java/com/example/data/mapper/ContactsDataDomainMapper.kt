package com.example.data.mapper

import com.example.common.Mapper
import com.example.data.model.ContactsItemDto
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
                uuid = i?.login?.uuid,
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
        )
    }

    override fun to(o: ContactsItemEntity?): ContactsItemDto {
        return ContactsItemDto()
    }
}