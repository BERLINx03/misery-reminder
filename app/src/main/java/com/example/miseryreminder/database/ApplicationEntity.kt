package com.example.miseryreminder.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "applications")
data class ApplicationEntity(
    @PrimaryKey(autoGenerate = true) var applicationId: Int = 0,
    var companyName: String,
    var applyingDate: Long = System.currentTimeMillis(),
    var applicationStatus: Status = Status.PENDING
)
