package com.touchsurgery.procedures

import com.touchsurgery.database.usecases.ObserveProceduresUseCase
import com.touchsurgery.procedures.TestData.procedureDetailsResponse1
import com.touchsurgery.procedures.TestData.procedureWithDetails1
import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.usecases.ObserveFavoriteProceduresUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ObserveFavoriteProceduresUseCaseTest {

    private lateinit var observeFavoriteProceduresUseCase: ObserveFavoriteProceduresUseCase
    private lateinit var observeProceduresUseCase: ObserveProceduresUseCase

    @Before
    fun setup() {
        observeProceduresUseCase = mock()

        observeFavoriteProceduresUseCase =
            ObserveFavoriteProceduresUseCase(observeProceduresUseCase)
    }

    @Test
    fun `use case returns correct list of procedures`() = runTest {

        whenever(observeProceduresUseCase.invoke())
            .thenReturn(flowOf(listOf(procedureWithDetails1)))

        val result: List<ProcedureDetails> = observeFavoriteProceduresUseCase()
            .take(1)
            .toList()
            .first()

        val expected =
            listOf(ProcedureDetails.fromResponse(procedureDetailsResponse1, isFavorite = true))

        assertEquals(expected, result)
    }
}
