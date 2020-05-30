package com.example.individualassignment.model

import com.google.gson.annotations.SerializedName

data class EmptyContainerObject(

    @SerializedName("")
    var results: List<PrayerResult>
)