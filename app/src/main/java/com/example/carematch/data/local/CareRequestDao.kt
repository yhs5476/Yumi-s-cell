package com.example.carematch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.carematch.data.model.CareRequest
import com.example.carematch.data.model.RequestStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface CareRequestDao {
    @Query("SELECT * FROM care_requests ORDER BY createdAt DESC")
    fun getAllRequests(): Flow<List<CareRequest>>

    @Query("SELECT * FROM care_requests WHERE caregiverId = :caregiverId ORDER BY createdAt DESC")
    fun getRequestsForCaregiver(caregiverId: Long): Flow<List<CareRequest>>

    @Query("SELECT * FROM care_requests WHERE guardianId = :guardianId ORDER BY createdAt DESC")
    fun getRequestsForGuardian(guardianId: String): Flow<List<CareRequest>>

    @Query("SELECT * FROM care_requests WHERE requestId = :requestId LIMIT 1")
    fun getRequestById(requestId: Long): Flow<CareRequest?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: CareRequest): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequests(requests: List<CareRequest>)

    @Update
    suspend fun updateRequest(request: CareRequest)

    @Query("UPDATE care_requests SET status = :status, rejectionReason = :reason, updatedAt = :timestamp WHERE requestId = :requestId")
    suspend fun updateRequestStatus(requestId: Long, status: RequestStatus, reason: String?, timestamp: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM care_requests")
    suspend fun getCount(): Int
}
