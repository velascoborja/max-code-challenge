package com.touchsurgery.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.touchsurgery.database.entities.ProcedureEntity
import com.touchsurgery.database.entities.ProcedurePhaseCrossRefEntity
import com.touchsurgery.database.entities.ProcedureWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcedureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: ProcedureEntity)

    @Delete
    suspend fun delete(vararg items: ProcedureEntity)

    @Query("DELETE FROM procedures WHERE procedureId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM procedures")
    suspend fun getAll(): List<ProcedureEntity>

    @Query("SELECT procedureId FROM procedures")
    suspend fun getAllProcedureIds(): List<String>

    @Query("SELECT * FROM procedures WHERE procedureId IN (:ids)")
    suspend fun getProcedures(vararg ids: String): List<ProcedureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg crossRef: ProcedurePhaseCrossRefEntity)

    @Transaction
    @Query("SELECT * FROM procedures")
    suspend fun getAllProceduresWithDetails(): List<ProcedureWithDetails>

    @Transaction
    @Query("SELECT * FROM procedures")
    fun observeAllProceduresWithDetails(): Flow<List<ProcedureWithDetails>>

    @Query("SELECT * FROM procedure_phase_cross_ref")
    suspend fun getAllCrossRefs(): List<ProcedurePhaseCrossRefEntity>

    @Query("SELECT * FROM procedure_phase_cross_ref WHERE procedureId = :id")
    suspend fun getCrossRefsForProcedure(id: String): List<ProcedurePhaseCrossRefEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM procedures WHERE procedureId = :id)")
    suspend fun exists(id: String): Boolean
}
