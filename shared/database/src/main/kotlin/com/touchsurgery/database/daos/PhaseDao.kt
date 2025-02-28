package com.touchsurgery.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.touchsurgery.database.entities.OrphanedPhaseId
import com.touchsurgery.database.entities.PhaseEntity
import com.touchsurgery.database.entities.PhaseWithDetails

@Dao
interface PhaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: PhaseEntity)

    @Delete
    suspend fun delete(vararg items: PhaseEntity)

    @Query("DELETE FROM phases WHERE phaseId IN (:phaseIds)")
    suspend fun deletePhasesByIds(phaseIds: List<String>)

    @Query("SELECT * FROM phases")
    suspend fun getAll(): List<PhaseEntity>

    @Query("SELECT * FROM OrphanedPhaseIdView")
    suspend fun getOrphanedPhaseIds(): List<OrphanedPhaseId>

    @Transaction
    @Query("SELECT * FROM phases")
    suspend fun getPhasesWithDetails(): List<PhaseWithDetails>

    @Transaction
    @Query("SELECT * FROM phases WHERE phaseId IN (:ids)")
    suspend fun getPhasesWithDetails(vararg ids: String): List<PhaseWithDetails>

}
