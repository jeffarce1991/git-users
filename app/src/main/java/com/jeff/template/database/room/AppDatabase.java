package com.jeff.template.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeff.template.database.local.Photo;
import com.jeff.template.database.room.converter.PhotoConverter;
import com.jeff.template.database.room.dao.PhotoDao;

@Database(
        entities = {
                Photo.class
        },
        version = 3,
        exportSchema = false
)

@TypeConverters(
        {
                PhotoConverter.class
        })
public abstract class AppDatabase extends RoomDatabase {
        public abstract PhotoDao photoDao();
}
