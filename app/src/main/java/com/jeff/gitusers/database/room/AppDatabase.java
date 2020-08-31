package com.jeff.gitusers.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeff.gitusers.database.local.Notes;
import com.jeff.gitusers.database.local.User;
import com.jeff.gitusers.database.local.UserDetails;
import com.jeff.gitusers.database.room.converter.UserConverter;
import com.jeff.gitusers.database.room.dao.NotesDao;
import com.jeff.gitusers.database.room.dao.UserDao;
import com.jeff.gitusers.database.room.dao.UserDetailsDao;

@Database(
        entities = {
                User.class,
                UserDetails.class,
                Notes.class
        },
        version = 23,
        exportSchema = false
)

@TypeConverters(
        {
                UserConverter.class
        })
public abstract class AppDatabase extends RoomDatabase {
        public abstract UserDao userDao();
        public abstract UserDetailsDao userDetailsDao();
        public abstract NotesDao notesDao();
}
