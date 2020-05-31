package com.example.individualassignment.model.apimodel

import androidx.room.Entity
import com.example.individualassignment.model.apimodel.PrayerDetails
import com.google.gson.annotations.SerializedName


@Entity(tableName = "prayer_results_table")
data class ResultObject(
    @SerializedName("data")
    var data: Array<PrayerDetails>
)
