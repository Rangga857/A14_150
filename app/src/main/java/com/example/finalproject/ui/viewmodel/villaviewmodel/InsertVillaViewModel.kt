package com.example.finalproject.ui.viewmodel.villaviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Villa
import com.example.finalproject.repository.VillaRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val villaRepository: VillaRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertVillaUiState())

    fun updateInsertVillaState(insertVillaUiEvent: InsertVillaUiEvent) {
        uiState = uiState.copy(insertVillaUiEvent = insertVillaUiEvent)
    }

    fun validateFields(): Boolean {
        val event = uiState.insertVillaUiEvent
        val errorState = FormErrorStateVilla(
            idVilla = if (event.idVla.isNotEmpty()) null else "ID Villa tidak boleh kosong",
            namaVilla = if (event.namaVilla.isNotEmpty()) null else "Nama Villa tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kamarTersedia = if (event.kamarTersedia >= 0) null else "Jumlah kamar tidak valid"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.insertVillaUiEvent
        Log.d("InsertVilla", "Data to save: $currentEvent")
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    villaRepository.insertVilla(currentEvent.toVilla())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        insertVillaUiEvent = InsertVillaUiEvent(),
                        isEntryValid = FormErrorStateVilla()
                    )
                } catch (e: Exception) {
                    Log.e("InsertVilla", "Error saving data: ${e.message}")
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan: ${e.message}"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data yang Anda masukkan"
            )
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}


data class InsertVillaUiState(
    val insertVillaUiEvent: InsertVillaUiEvent = InsertVillaUiEvent(),
    val isEntryValid: FormErrorStateVilla = FormErrorStateVilla(),
    val snackBarMessage: String? = null
)

data class FormErrorStateVilla(
    val idVilla: String? = null,
    val namaVilla: String? = null,
    val alamat: String? = null,
    val kamarTersedia: String? = null
) {
    fun isValid(): Boolean {
        return idVilla == null && namaVilla == null && alamat == null && kamarTersedia == null
    }
}

data class InsertVillaUiEvent(
    val idVla: String = "",
    val namaVilla: String = "",
    val alamat: String = "",
    val kamarTersedia: Int = 0
)

fun InsertVillaUiEvent.toVilla(): Villa = Villa(
    idVilla = idVla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamarTersedia = kamarTersedia
)

fun Villa.toVillaUiState(): InsertVillaUiState = InsertVillaUiState(
    insertVillaUiEvent = toInsertVillaUiEvent()
)

fun Villa.toInsertVillaUiEvent(): InsertVillaUiEvent = InsertVillaUiEvent(
    idVla = idVilla,
    namaVilla = namaVilla,
    alamat = alamat,
    kamarTersedia = kamarTersedia
)