package com.example.miseryreminder.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlin.jvm.java

@Database(
    entities = [ApplicationEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract val applicationDao: ApplicationDao
    companion object {
        @Volatile
        var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = ApplicationDatabase::class.java,
                        name = "applications_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}