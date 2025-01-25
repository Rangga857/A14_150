package com.example.finalproject.ui.viewmodel.pelangganviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.navigation.DestinasiEditPelanggan
import com.example.finalproject.navigation.DestinasiEditReservasi
import com.example.finalproject.repository.PelangganRepository
import kotlinx.coroutines.launch

class EditPelangganViewModel(
    savedStateHandle: SavedStateHandle,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {

    var uiPelangganState by mutableStateOf(InsertPelangganUiState())
        private set

    val idPelanggan: Int = checkNotNull(savedStateHandle[DestinasiEditPelanggan.idPelanggan])

    init {
        viewModelScope.launch {
            try {
                val pelanggan = pelangganRepository.getPelangganById(idPelanggan)
                uiPelangganState = pelanggan.toPelangganUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePelangganState(
        insertPelangganUiEvent: InsertPelangganUiEvent
    ) {
        uiPelangganState = InsertPelangganUiState(insertPelangganUiEvent = insertPelangganUiEvent)
    }

    suspend fun updatePelanggan() {
        viewModelScope.launch {
            try {
                val pelanggan = uiPelangganState.insertPelangganUiEvent.toPelanggan()
                pelangganRepository.updatePelanggan(idPelanggan, pelanggan)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
