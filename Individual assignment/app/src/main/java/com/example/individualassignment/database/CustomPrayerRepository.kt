package com.example.individualassignment.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.individualassignment.model.CustomPrayer

class CustomPrayerRepository(context: Context) {

    var customPrayerDao: CustomPrayerDao

    init {
        val database =
            CustomPrayerRoomDatabase.getDatabase(context)
        customPrayerDao = database!!.customPrayerDao()
    }


    fun getAllCustomPrayers() : LiveData<List<CustomPrayer>> {
        return customPrayerDao.getAllCustomPrayers()
    }



    suspend fun insertCustomPrayer(customPrayer: CustomPrayer)= customPrayerDao.insertCustomPrayer(customPrayer)
    suspend fun deleteCustomPrayer(customPrayer: CustomPrayer) = customPrayerDao.deleteCustomPrayer(customPrayer)
    suspend fun deleteAllCustomPrayers()= customPrayerDao.deleteAllCustomPrayers()



}