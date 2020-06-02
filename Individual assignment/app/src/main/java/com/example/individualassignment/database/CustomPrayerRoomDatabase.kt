package com.example.individualassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.individualassignment.model.CustomPrayer

@Database(entities = [CustomPrayer::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CustomPrayerRoomDatabase : RoomDatabase() {
    abstract fun customPrayerDao(): CustomPrayerDao

    companion object {
        private const val DATABASE_NAME = "CUSTOM_PRAYER_DATABASE"

        @Volatile
        private var customPrayerTimeRoomDatabaseInstace: CustomPrayerRoomDatabase? = null

        fun getDatabase(context: Context): CustomPrayerRoomDatabase? {
            if (customPrayerTimeRoomDatabaseInstace == null) {
                synchronized(CustomPrayerRoomDatabase::class.java) {
                    if (customPrayerTimeRoomDatabaseInstace == null) {
                        customPrayerTimeRoomDatabaseInstace =
                            Room.databaseBuilder(
                                context.applicationContext,
                                CustomPrayerRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return customPrayerTimeRoomDatabaseInstace
        }
    }

}