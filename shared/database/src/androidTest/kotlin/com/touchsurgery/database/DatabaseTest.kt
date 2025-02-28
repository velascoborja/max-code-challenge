package com.touchsurgery.database

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.touchsurgery.database.TestData.crossRef1
import com.touchsurgery.database.TestData.crossRef2
import com.touchsurgery.database.TestData.iconPhase1
import com.touchsurgery.database.TestData.iconPhase2
import com.touchsurgery.database.TestData.iconProcedure
import com.touchsurgery.database.TestData.phase1
import com.touchsurgery.database.TestData.phase2
import com.touchsurgery.database.TestData.procedure1
import com.touchsurgery.database.daos.IconDao
import com.touchsurgery.database.daos.PhaseDao
import com.touchsurgery.database.daos.ProcedureDao
import com.touchsurgery.database.entities.PhaseWithDetails
import com.touchsurgery.database.entities.ProcedureWithDetails
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db: DigitalSurgeryDatabase
    private lateinit var iconDao: IconDao
    private lateinit var phaseDao: PhaseDao
    private lateinit var procedureDao: ProcedureDao


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            DigitalSurgeryDatabase::class.java,
        ).build()

        iconDao = db.getIconDao()
        phaseDao = db.getPhaseDao()
        procedureDao = db.getProcedureDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeProcedureAndReadWithDetails() {

        runBlocking {
            db.withTransaction {
                iconDao.insert(iconProcedure, iconPhase1, iconPhase2)
                phaseDao.insert(phase1, phase2)
                procedureDao.insert(procedure1)
                procedureDao.insert(crossRef1, crossRef2)
            }
        }

        val result = runBlocking { procedureDao.getAllProceduresWithDetails() }
        assertThat(
            result.first(),
            equalTo(
                ProcedureWithDetails(
                    procedure = procedure1,
                    icon = iconProcedure,
                    phases = listOf(
                        PhaseWithDetails(phase1, icon = iconPhase1),
                        PhaseWithDetails(phase2, icon = iconPhase2),
                    ),
                )
            ),
        )
    }

    @Test
    @Throws(Exception::class)
    fun deleteProcedureAndVerifyCascadeDeletion() {
        runBlocking {
            db.withTransaction {
                iconDao.insert(iconProcedure, iconPhase1, iconPhase2)
                phaseDao.insert(phase1, phase2)
                procedureDao.insert(procedure1)
                procedureDao.insert(crossRef1, crossRef2)
            }
        }

        runBlocking {
            procedureDao.delete(procedure1)
        }

        val crossRefs = runBlocking {
            procedureDao.getCrossRefsForProcedure(procedure1.procedureId)
        }

        assertThat(crossRefs, equalTo(emptyList()))
    }

    @Test
    @Throws(Exception::class)
    fun deleteProcedureAndVerifyIconsDeletion() {
        runBlocking {
            db.withTransaction {
                iconDao.insert(iconProcedure, iconPhase1, iconPhase2)
                phaseDao.insert(phase1, phase2)
                procedureDao.insert(procedure1)
                procedureDao.insert(crossRef1, crossRef2)
            }
        }

        runBlocking {
            procedureDao.delete(procedure1)
        }

        val orphanedIconIds = runBlocking {
            iconDao.getOrphanedIconIds().map { it.iconId }
        }

        assertThat(orphanedIconIds, equalTo(listOf(iconProcedure.iconId)))

        runBlocking {
            iconDao.deleteIconsByIds(orphanedIconIds)
        }

        val iconsLeft = runBlocking {
            iconDao.getAll()
        }

        assertThat(iconsLeft.none { it.iconId == iconProcedure.iconId }, equalTo(true))
    }
}
