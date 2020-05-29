package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "prayer_table")
data class PrayerObject (

    @SerializedName("data")
    var prayerTime: String

)
