package com.jeff.gitusers.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeff.gitusers.database.local.Photo;
import com.jeff.gitusers.database.local.User;
import com.jeff.gitusers.database.local.UserDetails;
import com.jeff.gitusers.database.room.converter.PhotoConverter;
import com.jeff.gitusers.database.room.converter.UserConverter;
import com.jeff.gitusers.database.room.converter.UserDetailsConverter;
import com.jeff.gitusers.database.room.dao.PhotoDao;
import com.jeff.gitusers.database.room.dao.UserDao;

@Database(
        entities = {
                User.class,
                UserDetails.class
        },
        version = 15,
        exportSchema = false
)

@TypeConverters(
        {
                UserConverter.class
        })
public abstract class AppDatabase extends RoomDatabase {
        public abstract UserDao userDao();
}
