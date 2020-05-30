package com.example.individualassignment.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "prayer_results_table")
data class ResultObject(
    @SerializedName("status")
    var status: String,

    @SerializedName("code")
    var code: String,

    @SerializedName("data")
    var data: Array<EmptyArrayObject>


)
