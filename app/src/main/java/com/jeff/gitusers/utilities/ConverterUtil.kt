package com.jeff.template.utilities

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.lang.reflect.Type

/**
 * Utility class for converting various types from and to [String].
 */
object ConverterUtil {

    private var gson: Gson? = null

    @JvmStatic
    fun <T> deserialise(serialisedType: String, type: Type): T {
        return getGson().fromJson(serialisedType, type)
    }

    @JvmStatic
    fun <T> serialise(type: T): String {
        return getGson().toJson(type)
    }

    private fun getGson(): Gson {
        if (gson == null) gson = GsonBuilder().create()
        return gson!!
    }
}
