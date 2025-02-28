package com.touchsurgery.procedures

import com.touchsurgery.database.usecases.RemoveProcedureUseCase
import com.touchsurgery.database.usecases.SaveProcedureUseCase
import com.touchsurgery.procedures.TestData.procedureDetailsResponse1
import com.touchsurgery.procedures.TestData.procedurePreviewResponse1
import com.touchsurgery.procedures.TestData.procedureWithDetails1
import com.touchsurgery.procedures.models.ProcedureDetails
import com.touchsurgery.procedures.models.ProcedurePreview
import com.touchsurgery.procedures.usecases.FetchProcedureDetailsUseCase
import com.touchsurgery.procedures.usecases.ToggleFavoriteProcedureUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class ToggleFavoriteProcedureUseCaseTest {

    private lateinit var toggleFavoriteProcedureUseCase: ToggleFavoriteProcedureUseCase

    private lateinit var fetchProcedureDetailsUseCase: FetchProcedureDetailsUseCase
    private lateinit var removeProcedureUseCase: RemoveProcedureUseCase
    private lateinit var saveProcedureUseCase: SaveProcedureUseCase

    @Before
    fun setup() {
        fetchProcedureDetailsUseCase = mock()
        removeProcedureUseCase = mock()
        saveProcedureUseCase = mock()


        toggleFavoriteProcedureUseCase = ToggleFavoriteProcedureUseCase(
            fetchProcedureDetailsUseCase,
            removeProcedureUseCase,
            saveProcedureUseCase,
        )
    }

    @Test
    fun `use case removes procedure details when it is favorite`() = runTest {
        val procedure = ProcedureDetails.fromResponse(procedureDetailsResponse1, isFavorite = true)

        toggleFavoriteProcedureUseCase.invoke(procedure)

        verify(removeProcedureUseCase).invoke(procedure.id)
    }

    @Test
    fun `use case removes procedure preview when it is favorite`() = runTest {
        val procedure = ProcedurePreview.fromResponse(procedurePreviewResponse1, isFavorite = true)

        toggleFavoriteProcedureUseCase.invoke(procedure)

        verify(removeProcedureUseCase).invoke(procedure.id)
    }

    @Test
    fun `use case saves procedure details when it is not favorite`() = runTest {
        val procedure = ProcedureDetails.fromResponse(procedureDetailsResponse1, isFavorite = false)

        toggleFavoriteProcedureUseCase.invoke(procedure)

        val expected = procedureWithDetails1

        verify(saveProcedureUseCase).invoke(expected)
    }

    @Test
    fun `use case fetches and saves procedure preview when it is not favorite`() = runTest {
        val procedure = ProcedurePreview.fromResponse(procedurePreviewResponse1, isFavorite = false)
        val procedureDetails =
            ProcedureDetails.fromResponse(procedureDetailsResponse1, isFavorite = false)

        whenever(fetchProcedureDetailsUseCase.invoke(procedure.id))
            .thenReturn(procedureDetails)

        toggleFavoriteProcedureUseCase.invoke(procedure)

        val expected = procedureWithDetails1

        verify(fetchProcedureDetailsUseCase).invoke(procedure.id)
        verify(saveProcedureUseCase).invoke(expected)
    }
}
