package com.example.miseryreminder.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {
    @Upsert
    suspend fun upsertApplication(application: ApplicationEntity)

    @Delete
    suspend fun deleteApplication(application: ApplicationEntity)

    @Delete
    suspend fun deleteSelected(vararg applications: ApplicationEntity)

    @Query("SELECT * FROM applications")
    fun getAllApplications(): Flow<List<ApplicationEntity>>

    @Query("SELECT * FROM applications WHERE applicationId = :id")
    suspend fun getApplicationById(id: Int): ApplicationEntity?
}