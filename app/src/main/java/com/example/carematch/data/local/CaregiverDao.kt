package com.example.carematch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.carematch.data.model.CaregiverProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface CaregiverDao {
    @Query("SELECT * FROM caregiver_profiles WHERE isActive = 1 ORDER BY rating DESC, completedCases DESC")
    fun getAllActiveCaregivers(): Flow<List<CaregiverProfile>>

    @Query("SELECT * FROM caregiver_profiles ORDER BY profileId ASC")
    fun getAllCaregivers(): Flow<List<CaregiverProfile>>

    @Query("SELECT * FROM caregiver_profiles WHERE profileId = :profileId LIMIT 1")
    fun getCaregiverById(profileId: Long): Flow<CaregiverProfile?>

    @Query("SELECT * FROM caregiver_profiles WHERE userId = :userId LIMIT 1")
    fun getCaregiverByUserId(userId: String): Flow<CaregiverProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaregiver(caregiver: CaregiverProfile): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaregivers(caregivers: List<CaregiverProfile>)

    @Update
    suspend fun updateCaregiver(caregiver: CaregiverProfile)

    @Query("SELECT COUNT(*) FROM caregiver_profiles")
    suspend fun getCount(): Int
}
