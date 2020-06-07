package com.example.individualassignment.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Time
import java.util.*

@Parcelize
@Entity(tableName = "custom_Prayer_table")
data class CustomPrayer (
    var prayerName: String,
    var startTime: Date,
    var endTime: Date,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
) : Parcelable