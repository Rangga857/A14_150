package com.example.finalproject.ui.viewmodel.reservasiviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.navigation.DestinasiEditReservasi
import com.example.finalproject.repository.ReservasiRepository
import kotlinx.coroutines.launch

class EditReservasiViewModel(
    savedStateHandle: SavedStateHandle,
    private val reservasiRepository: ReservasiRepository
) : ViewModel() {

    var uiReservasiState by mutableStateOf(InsertReservasiUiState())
        private set

    val idReservasi: Int = checkNotNull(savedStateHandle[DestinasiEditReservasi.idReservasi])

    init {
        viewModelScope.launch {
            try {
                val reservasi = reservasiRepository.getReservasiById(idReservasi)
                uiReservasiState = reservasi.toReservasiUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateReservasiState(
        insertReservasiUiEvent: InsertReservasiUiEvent
    ) {
        uiReservasiState = InsertReservasiUiState(insertReservasiUiEvent = insertReservasiUiEvent)
    }

    suspend fun updateReservasi() {
        viewModelScope.launch {
            try {
                val reservasi = uiReservasiState.insertReservasiUiEvent.toReservasi()
                reservasiRepository.updateReservasi(idReservasi, reservasi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
