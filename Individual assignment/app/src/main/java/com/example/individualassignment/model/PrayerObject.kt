package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "prayer_table")
data class PrayerObject(

    @SerializedName("Fajr")
    var fajr: String,

    @SerializedName("Sunrise")
    var sunrise: String,

    @SerializedName("Dhuhr")
    var duhr: String,

    @SerializedName("Asr")
    var asr: String,

    @SerializedName("Maghrib")
    var maghrib: String,

    @SerializedName("Isha")
    var isha: String

    )
