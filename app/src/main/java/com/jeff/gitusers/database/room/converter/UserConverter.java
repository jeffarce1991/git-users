package com.jeff.gitusers.database.room.converter;

import androidx.room.TypeConverter;

import com.jeff.gitusers.database.local.Photo;
import com.jeff.gitusers.database.local.User;
import com.jeff.gitusers.utilities.ConverterUtil;

public class UserConverter {
    private UserConverter() { }

    @TypeConverter
    public static String fromUser(User user) {
        return ConverterUtil.serialise(user);
    }

    @TypeConverter
    public static User toUser(String serialised) {
        return ConverterUtil.deserialise(serialised, User.class);
    }
}
