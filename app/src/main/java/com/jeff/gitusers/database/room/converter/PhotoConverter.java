package com.jeff.gitusers.database.room.converter;

import androidx.room.TypeConverter;

import com.jeff.gitusers.database.local.Photo;
import com.jeff.gitusers.utilities.ConverterUtil;

public class PhotoConverter {
    private PhotoConverter() { }

    @TypeConverter
    public static String fromPhoto(Photo photo) {
        return ConverterUtil.serialise(photo);
    }

    @TypeConverter
    public static Photo toPhoto(String serialised) {
        return ConverterUtil.deserialise(serialised, Photo.class);
    }
}
