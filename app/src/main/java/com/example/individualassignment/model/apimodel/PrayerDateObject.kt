package com.example.individualassignment.model.apimodel

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "prayer_date_table")
data class PrayerDateObject (
    @SerializedName("readable")
    var readable: String

)