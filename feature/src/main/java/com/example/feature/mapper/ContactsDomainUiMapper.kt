package com.example.feature.mapper

import com.example.common.Mapper
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.entity.IdEntity
import com.example.domain.entity.LocationEntity
import com.example.domain.entity.LoginEntity
import com.example.domain.entity.NameEntity
import com.example.domain.entity.PictureEntity
import com.example.domain.entity.StreetEntity
import com.example.feature.models.ContactsItemUiModel
import com.example.feature.models.IdUiModel
import com.example.feature.models.LocationUiModel
import com.example.feature.models.LoginUiModel
import com.example.feature.models.NameUiModel
import com.example.feature.models.PictureUiModel
import com.example.feature.models.StreetUiModel
import javax.inject.Inject

class ContactsDomainUiMapper @Inject constructor() :
    Mapper<ContactsItemEntity, ContactsItemUiModel> {

    override fun from(i: ContactsItemEntity?): ContactsItemUiModel {
        return ContactsItemUiModel(
            id = IdUiModel(name = i?.id?.name.orEmpty(), value = i?.id?.value.orEmpty()),
            phone = i?.phone,
            name = NameUiModel(
                first = i?.name?.first,
                last = i?.name?.last,
                title = i?.name?.title
            ),
            location = LocationUiModel(
                country = i?.location?.country,
                city = i?.location?.city,
                state = i?.location?.state,
                postcode = i?.location?.postcode,
                street = StreetUiModel(
                    number = i?.location?.street?.number,
                    name = i?.location?.street?.name
                ),
            ),
            cell = i?.cell,
            email = i?.email,
            picture = PictureUiModel(
                thumbnail = i?.picture?.thumbnail,
                large = i?.picture?.large,
                medium = i?.picture?.medium
            ),
            login = LoginUiModel(
                sha1 = i?.login?.sha1,
                password = i?.login?.password,
                salt = i?.login?.salt,
                sha256 = i?.login?.sha256,
                uuid = i?.login?.uuid.orEmpty(),
                username = i?.login?.username,
                md5 = i?.login?.md5
            ),
            isFavorite = i?.isFavorite ?: false
        )
    }

    override fun to(o: ContactsItemUiModel?): ContactsItemEntity {
        return ContactsItemEntity(
            id = IdEntity(name = o?.id?.name, value = o?.id?.value),
            phone = o?.phone,
            name = NameEntity(
                first = o?.name?.first,
                last = o?.name?.last,
                title = o?.name?.title
            ),
            location = LocationEntity(
                country = o?.location?.country,
                city = o?.location?.city,
                state = o?.location?.state,
                postcode = o?.location?.postcode,
                street = StreetEntity(
                    number = o?.location?.street?.number,
                    name = o?.location?.street?.name
                ),
            ),
            cell = o?.cell,
            email = o?.email,
            picture = PictureEntity(
                thumbnail = o?.picture?.thumbnail,
                large = o?.picture?.large,
                medium = o?.picture?.medium
            ),
            login = LoginEntity(
                sha1 = o?.login?.sha1,
                password = o?.login?.password,
                salt = o?.login?.salt,
                sha256 = o?.login?.sha256,
                uuid = o?.login?.uuid.orEmpty(),
                username = o?.login?.username,
                md5 = o?.login?.md5
            ),
            isFavorite = o?.isFavorite
        )
    }
}