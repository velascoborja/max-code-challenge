package com.touchsurgery.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.touchsurgery.database.TestData.procedureWithDetails
import com.touchsurgery.database.daos.ProcedureDao
import com.touchsurgery.database.entities.ProcedureWithDetails
import com.touchsurgery.database.usecases.SaveProcedureUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SaveProcedureUseCaseTest {

    private lateinit var db: DigitalSurgeryDatabase
    private lateinit var procedureDao: ProcedureDao
    private lateinit var saveProcedureUseCase: SaveProcedureUseCase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context,
            DigitalSurgeryDatabase::class.java,
        ).build()

        procedureDao = db.getProcedureDao()
        saveProcedureUseCase = SaveProcedureUseCase(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveProcedure_insertsAllDataCorrectly() = runBlocking {
        saveProcedureUseCase(procedureWithDetails)

        val result: List<ProcedureWithDetails> = procedureDao.getAllProceduresWithDetails()

        assertEquals(1, result.size)
        assertEquals(procedureWithDetails, result.first())
    }
}
