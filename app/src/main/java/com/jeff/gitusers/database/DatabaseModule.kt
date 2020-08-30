package com.jeff.gitusers.database

import android.app.Application
import androidx.room.Room
import com.jeff.gitusers.R
import com.jeff.gitusers.database.room.AppDatabase
import com.jeff.gitusers.database.room.dao.PhotoDao
import com.jeff.gitusers.database.room.dao.UserDao
import com.jeff.gitusers.database.usecase.local.LocalUseCaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalUseCaseModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,
            AppDatabase::class.java,
            application.getString(R.string.db_name))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}