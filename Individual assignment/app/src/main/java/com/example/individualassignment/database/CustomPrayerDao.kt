package com.example.individualassignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.individualassignment.model.CustomPrayer


@Dao

interface CustomPrayerDao {

    @Query("SELECT * FROM custom_Prayer_table ORDER BY prayerDate DESC")
    fun getAllCustomPrayers(): LiveData<List<CustomPrayer>>

    @Insert
    suspend fun insertCustomPrayer(customPrayer: CustomPrayer)

    @Delete
    fun deleteCustomPrayer(customPrayer: CustomPrayer)

    @Query("DELETE FROM custom_Prayer_table")
    suspend fun deleteAllCustomPrayers()

}