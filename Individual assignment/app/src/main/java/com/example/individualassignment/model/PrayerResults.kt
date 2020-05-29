package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "prayer_results_table")
data class PrayerResults(
    @SerializedName("status")
    var status: String,
    @SerializedName("data")
    var results: TimesObject


)
