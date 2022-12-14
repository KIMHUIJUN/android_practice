package com.example.ch16_provider.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(ImageEntity::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getImageDao() : ImageDao

    companion object{
        val databaseName = "db_Image"
        var appDatabase: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase?{
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    databaseName).build()
            }
            return appDatabase
        }
    }
}