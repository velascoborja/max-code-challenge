package com.touchsurgery.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.touchsurgery.database.converters.DateTimeConverter
import com.touchsurgery.database.daos.IconDao
import com.touchsurgery.database.daos.PhaseDao
import com.touchsurgery.database.daos.ProcedureDao
import com.touchsurgery.database.entities.IconEntity
import com.touchsurgery.database.entities.OrphanedIconId
import com.touchsurgery.database.entities.OrphanedPhaseId
import com.touchsurgery.database.entities.PhaseEntity
import com.touchsurgery.database.entities.ProcedureEntity
import com.touchsurgery.database.entities.ProcedurePhaseCrossRefEntity

@Database(
    entities = [
        IconEntity::class,
        ProcedureEntity::class,
        PhaseEntity::class,
        ProcedurePhaseCrossRefEntity::class,
    ],
    views = [
        OrphanedIconId::class,
        OrphanedPhaseId::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(DateTimeConverter::class)
abstract class DigitalSurgeryDatabase : RoomDatabase() {

    abstract fun getIconDao(): IconDao

    abstract fun getPhaseDao(): PhaseDao

    abstract fun getProcedureDao(): ProcedureDao

}
