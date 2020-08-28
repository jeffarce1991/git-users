package com.jeff.template.database

import android.app.Application
import androidx.room.Room
import com.jeff.template.R
import com.jeff.template.database.room.AppDatabase
import com.jeff.template.database.room.dao.PhotoDao
import com.jeff.template.database.usecase.local.LocalUseCaseModule
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
    fun provideAssignmentDao(appDatabase: AppDatabase): PhotoDao {
        return appDatabase.photoDao()
    }
}