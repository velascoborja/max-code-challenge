package com.touchsurgery.procedures

import com.touchsurgery.database.usecases.GetProcedureIdsUseCase
import com.touchsurgery.networking.api.DigitalSurgeryService
import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.usecases.FetchProcedureDetailsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class FetchProcedureDetailsUnitTest {

    private lateinit var service: DigitalSurgeryService
    private lateinit var fetchProcedureDetailsUseCase: FetchProcedureDetailsUseCase
    private lateinit var getProcedureIdsUseCase: GetProcedureIdsUseCase

    @Before
    fun setup() {
        service = mock()
        getProcedureIdsUseCase = mock()

        fetchProcedureDetailsUseCase = FetchProcedureDetailsUseCase(service, getProcedureIdsUseCase)
    }

    @Test
    fun `use case returns correct procedure details`() = runTest {
        val response = TestData.procedureDetailsResponse1

        whenever(service.getProcedureDetails(TestData.procedurePreviewResponse1.uuid)).thenReturn(
            response
        )
        whenever(getProcedureIdsUseCase.invoke()).thenReturn(listOf(TestData.procedurePreviewResponse1.uuid))

        val result =
            fetchProcedureDetailsUseCase(procedureId = TestData.procedurePreviewResponse1.uuid)

        val expected = ProcedureDetails.fromResponse(TestData.procedureDetailsResponse1, true)

        assertEquals(expected, result)
    }
}
