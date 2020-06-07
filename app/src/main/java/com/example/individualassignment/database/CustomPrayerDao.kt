package com.example.individualassignment.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.individualassignment.model.CustomPrayer


@Dao

interface CustomPrayerDao {

    @Query("SELECT * FROM custom_Prayer_table ORDER BY startTime DESC")
    fun getAllCustomPrayers(): LiveData<List<CustomPrayer>>

    @Insert
    suspend fun insertCustomPrayer(customPrayer: CustomPrayer)

    @Delete
    fun deleteCustomPrayer(customPrayer: CustomPrayer)

    @Update
    fun updatePrayer(customPrayer: CustomPrayer)

    @Query("DELETE FROM custom_Prayer_table")
    suspend fun deleteAllCustomPrayers()



}