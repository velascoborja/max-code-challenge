package com.touchsurgery.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.touchsurgery.database.entities.IconEntity
import com.touchsurgery.database.entities.OrphanedIconId

@Dao
interface IconDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: IconEntity)

    @Delete
    suspend fun delete(vararg items: IconEntity)

    @Query("DELETE FROM icons WHERE iconId IN (:iconIds)")
    suspend fun deleteIconsByIds(iconIds: List<String>)

    @Query("SELECT * FROM icons")
    suspend fun getAll(): List<IconEntity>

    @Query("SELECT * FROM OrphanedIconIdView")
    suspend fun getOrphanedIconIds(): List<OrphanedIconId>
}
