package com.example.finalproject.ui.viewmodel.pelangganviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Pelanggan
import com.example.finalproject.repository.PelangganRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class PagePelangganUiState{
    data class Success(val pelanggan: List<Pelanggan>) : PagePelangganUiState()
    object Error : PagePelangganUiState()
    object Loading : PagePelangganUiState()
}

class PagePelangganViewModel(
    private val pelanggan: PelangganRepository,
    private val pelangganRepository: PelangganRepository): ViewModel(){
    var pelangganUiState : PagePelangganUiState by mutableStateOf(PagePelangganUiState.Loading)
        private set

    init {
        getPelanggan()
    }

    fun getPelanggan() {
        viewModelScope.launch {
            pelangganUiState = PagePelangganUiState.Loading
            pelangganUiState = try {
                PagePelangganUiState.Success(pelanggan.getPelanggan())
            } catch (e: IOException) {
                PagePelangganUiState.Error
            }
        }
    }

    var pelangganuiState by mutableStateOf(InsertPelangganUiState())

    fun updateInsertPelangganState(insertPelangganUiEvent: InsertPelangganUiEvent) {
        pelangganuiState = pelangganuiState.copy(insertPelangganUiEvent = insertPelangganUiEvent)
    }

    fun validateFields(): Boolean {
        val event = pelangganuiState.insertPelangganUiEvent
        val errorState = FormErrorStatePelanggan(
            namaPelanggan = if (event.namaPelanggan.isNotEmpty()) null else "Nama Pelanggan tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "Nomor HP tidak boleh kosong",
        )
        pelangganuiState = pelangganuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = pelangganuiState.insertPelangganUiEvent
        Log.d("InsertPelanggan", "Data to save: $currentEvent")
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pelangganRepository.insertPelanggan(currentEvent.toPelanggan())
                    pelangganuiState = pelangganuiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        insertPelangganUiEvent = InsertPelangganUiEvent(),
                        isEntryValid = FormErrorStatePelanggan()
                    )
                    getPelanggan()
                } catch (e: Exception) {
                    Log.e("InsertVilla", "Error saving data: ${e.message}")
                    pelangganuiState = pelangganuiState.copy(
                        snackBarMessage = "Data Gagal Disimpan: ${e.message}"
                    )
                }
            }
        } else {
            pelangganuiState = pelangganuiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data yang Anda masukkan"
            )
        }
    }

    fun resetSnackBarMessage() {
        pelangganuiState = pelangganuiState.copy(snackBarMessage = null)
    }
}

data class InsertPelangganUiState(
    val insertPelangganUiEvent: InsertPelangganUiEvent = InsertPelangganUiEvent(),
    val isEntryValid: FormErrorStatePelanggan = FormErrorStatePelanggan(),
    val snackBarMessage: String? = null
)

data class FormErrorStatePelanggan(
    val namaPelanggan: String? = null,
    val noHp: String? = null
) {
    fun isValid(): Boolean {
        return namaPelanggan == null && noHp == null
    }
}

data class InsertPelangganUiEvent(
    val namaPelanggan: String = "",
    val noHp: String = ""
)

fun InsertPelangganUiEvent.toPelanggan(): Pelanggan = Pelanggan(
    idPelanggan = null,
    namaPelanggan = namaPelanggan,
    noHp = noHp
)

fun Pelanggan.toPelangganUiState(): InsertPelangganUiState = InsertPelangganUiState(
    insertPelangganUiEvent = toInsertPelangganUiEvent()
)

fun Pelanggan.toInsertPelangganUiEvent(): InsertPelangganUiEvent = InsertPelangganUiEvent(
    namaPelanggan = namaPelanggan,
    noHp = noHp,
)