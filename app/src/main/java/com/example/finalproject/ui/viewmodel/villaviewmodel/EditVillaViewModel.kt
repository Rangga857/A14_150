package com.example.finalproject.ui.viewmodel.villaviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.navigation.DestinasiEditVilla
import com.example.finalproject.repository.VillaRepository
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.InsertPelangganUiEvent
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.InsertPelangganUiState
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.toPelanggan
import kotlinx.coroutines.launch

class EditVillaViewModel(
    savedStateHandle: SavedStateHandle,
    private val villarepository: VillaRepository
) : ViewModel() {

    var uiVillaState by mutableStateOf(InsertVillaUiState())
        private set

    val idVilla: String = checkNotNull(savedStateHandle[DestinasiEditVilla.idVilla])

    init {
        viewModelScope.launch {
            try {
                val (villa, reviews) = villarepository.getVillaById(idVilla)
                uiVillaState = villa.toVillaUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateVillaState(
        insertVillaUiEvent: InsertVillaUiEvent
    ) {
        uiVillaState = InsertVillaUiState(insertVillaUiEvent = insertVillaUiEvent)
    }

    suspend fun updateVilla() {
        viewModelScope.launch {
            try {
                val villa = uiVillaState.insertVillaUiEvent.toVilla()
                villarepository.updateVilla(idVilla, villa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
