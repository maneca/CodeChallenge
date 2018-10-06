package com.example.principal.codechallenge.database

import android.arch.persistence.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListString(value: List<String>): String{

        var string = ""

        for(s in value){
            string += "$s,"
        }

        return string.trimEnd(',')
    }

    @TypeConverter
    fun toListString(value : String): List<String>{

        val elements = mutableListOf<String>()

        for(s in value.split(","))
            elements.plus(s)

        return elements
    }
}