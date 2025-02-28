package com.touchsurgery.procedures

import com.touchsurgery.database.usecases.GetProcedureIdsUseCase
import com.touchsurgery.networking.api.DigitalSurgeryService
import com.touchsurgery.procedures.models.ProcedurePreview
import com.touchsurgery.procedures.usecases.FetchProcedurePreviewsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class FetchProceduresUnitTest {

    private lateinit var service: DigitalSurgeryService
    private lateinit var fetchProcedurePreviewsUseCase: FetchProcedurePreviewsUseCase
    private lateinit var getProcedureIdsUseCase: GetProcedureIdsUseCase

    @Before
    fun setup() {
        service = mock()
        getProcedureIdsUseCase = mock()

        fetchProcedurePreviewsUseCase =
            FetchProcedurePreviewsUseCase(service, getProcedureIdsUseCase)
    }

    @Test
    fun `use case returns correct list of procedure preview`() = runTest {
        val response =
            listOf(TestData.procedurePreviewResponse1, TestData.procedurePreviewResponse2)

        whenever(service.getProcedures()).thenReturn(response)
        whenever(getProcedureIdsUseCase()).thenReturn(listOf(TestData.procedurePreviewResponse1.uuid))

        val result = fetchProcedurePreviewsUseCase()

        val expected = listOf(
            ProcedurePreview.fromResponse(TestData.procedurePreviewResponse1, true),
            ProcedurePreview.fromResponse(TestData.procedurePreviewResponse2, false),
        )
        assertEquals(expected, result)
    }

    @Test
    fun `use case returns empty list when service returns empty list`() = runTest {
        whenever(service.getProcedures()).thenReturn(emptyList())

        val result = fetchProcedurePreviewsUseCase()

        assertEquals(emptyList<ProcedurePreview>(), result)
    }
}
