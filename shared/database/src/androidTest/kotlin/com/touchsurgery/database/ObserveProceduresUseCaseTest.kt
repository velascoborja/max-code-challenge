package com.touchsurgery.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.touchsurgery.database.TestData.procedureWithDetails
import com.touchsurgery.database.usecases.ObserveProceduresUseCase
import com.touchsurgery.database.usecases.RemoveProcedureUseCase
import com.touchsurgery.database.usecases.SaveProcedureUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ObserveProceduresUseCaseTest {

    private lateinit var db: DigitalSurgeryDatabase
    private lateinit var saveProcedureUseCase: SaveProcedureUseCase
    private lateinit var observeProceduresUseCase: ObserveProceduresUseCase
    private lateinit var removeProcedureUseCaseTest: RemoveProcedureUseCase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context,
            DigitalSurgeryDatabase::class.java,
        ).build()

        saveProcedureUseCase = SaveProcedureUseCase(db)
        observeProceduresUseCase = ObserveProceduresUseCase(db)
        removeProcedureUseCaseTest = RemoveProcedureUseCase(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveProcedure_observerReturnsCorrectValue() = runBlocking {

        saveProcedureUseCase(procedureWithDetails)

        val flow = observeProceduresUseCase()

        val result = flow.take(1).toList().first()

        assertEquals(1, result.size)
        assertEquals(procedureWithDetails, result.first())
    }
}
