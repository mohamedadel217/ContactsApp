package com.example.local.converters

import androidx.room.TypeConverter
import com.example.local.models.DobLocal
import com.example.local.models.IdLocal
import com.example.local.models.LocationLocal
import com.example.local.models.LoginLocal
import com.example.local.models.NameLocal
import com.example.local.models.PictureLocal
import com.example.local.models.RegisteredLocal
import com.google.gson.Gson

class CustomTypesConverter {

    //IdLocal converter
    @TypeConverter
    fun idLocalToString(id: IdLocal): String = Gson().toJson(id)

    @TypeConverter
    fun stringToIdLocal(string: String): IdLocal = Gson().fromJson(string, IdLocal::class.java)

    //dobLocal converter
    @TypeConverter
    fun dobLocalToString(dob: DobLocal): String = Gson().toJson(dob)

    @TypeConverter
    fun stringToDobLocal(string: String): DobLocal = Gson().fromJson(string, DobLocal::class.java)

    //nameLocal converter
    @TypeConverter
    fun nameLocalToString(nameLocal: NameLocal): String = Gson().toJson(nameLocal)

    @TypeConverter
    fun stringToNameLocal(string: String): NameLocal = Gson().fromJson(string, NameLocal::class.java)

    //registeredLocal converter
    @TypeConverter
    fun registeredLocalToString(registered: RegisteredLocal): String = Gson().toJson(registered)

    @TypeConverter
    fun stringToRegisteredLocal(string: String): RegisteredLocal = Gson().fromJson(string, RegisteredLocal::class.java)

    //locationLocal converter
    @TypeConverter
    fun locationLocalToString(locationLocal: LocationLocal): String = Gson().toJson(locationLocal)

    @TypeConverter
    fun stringToLocationLocal(string: String): LocationLocal = Gson().fromJson(string, LocationLocal::class.java)

    //loginLocal converter
    @TypeConverter
    fun loginLocalToString(loginLocal: LoginLocal): String = Gson().toJson(loginLocal)

    @TypeConverter
    fun stringToLoginLocal(string: String): LoginLocal = Gson().fromJson(string, LoginLocal::class.java)

    //pictureLocal converter
    @TypeConverter
    fun pictureLocalToString(pictureLocal: PictureLocal): String = Gson().toJson(pictureLocal)

    @TypeConverter
    fun stringToPictureLocal(string: String): PictureLocal = Gson().fromJson(string, PictureLocal::class.java)
}