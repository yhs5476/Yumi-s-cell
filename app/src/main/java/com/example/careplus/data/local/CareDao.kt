package com.example.careplus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.careplus.data.model.BidStatus
import com.example.careplus.data.model.EscrowStatus
import com.example.careplus.data.model.RequestStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface CareDao {
    // Care Requests
    @Query("SELECT * FROM care_requests ORDER BY createdAt DESC")
    fun getAllRequests(): Flow<List<CareRequestEntity>>

    @Query("SELECT * FROM care_requests WHERE status = :status ORDER BY createdAt DESC")
    fun getRequestsByStatus(status: RequestStatus): Flow<List<CareRequestEntity>>

    @Query("SELECT * FROM care_requests WHERE id = :id")
    suspend fun getRequestById(id: Long): CareRequestEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: CareRequestEntity): Long

    @Update
    suspend fun updateRequest(request: CareRequestEntity)

    // Caregivers
    @Query("SELECT * FROM caregivers")
    fun getAllCaregivers(): Flow<List<CaregiverProfileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaregivers(caregivers: List<CaregiverProfileEntity>)

    // Care Bids
    @Query("SELECT * FROM care_bids WHERE requestId = :requestId ORDER BY dailyPrice ASC")
    fun getBidsForRequest(requestId: Long): Flow<List<CareBidEntity>>

    @Query("SELECT * FROM care_bids WHERE bidId = :bidId")
    suspend fun getBidById(bidId: Long): CareBidEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBid(bid: CareBidEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBids(bids: List<CareBidEntity>)

    @Query("UPDATE care_bids SET status = :status WHERE bidId = :bidId")
    suspend fun updateBidStatus(bidId: Long, status: BidStatus)

    // Chat Messages
    @Query("SELECT * FROM chat_messages WHERE bidId = :bidId ORDER BY timestamp ASC")
    fun getMessagesForBid(bidId: Long): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity): Long

    // Contracts
    @Query("SELECT * FROM contracts ORDER BY createdAt DESC")
    fun getAllContracts(): Flow<List<ContractEntity>>

    @Query("SELECT * FROM contracts WHERE contractId = :contractId")
    suspend fun getContractById(contractId: Long): ContractEntity?

    @Query("SELECT * FROM contracts WHERE bidId = :bidId LIMIT 1")
    suspend fun getContractByBidId(bidId: Long): ContractEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContract(contract: ContractEntity): Long

    @Query("UPDATE contracts SET escrowStatus = :status WHERE contractId = :contractId")
    suspend fun updateEscrowStatus(contractId: Long, status: EscrowStatus)

    @Query("UPDATE contracts SET isReviewed = 1, ratingGiven = :rating, reviewComment = :comment WHERE contractId = :contractId")
    suspend fun submitReview(contractId: Long, rating: Float, comment: String)
}
