package com.example.finalproject.ui.viewmodel.reservasiviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Reservasi
import com.example.finalproject.repository.ReservasiRepository
import kotlinx.coroutines.launch

class InsertReservasiModel(
    private val reservasiRepository: ReservasiRepository,
): ViewModel() {
    var reservasiuiState by mutableStateOf(
        InsertReservasiUiState(insertReservasiUiEvent = InsertReservasiUiEvent())
    )

    fun updateInsertVillaState(insertReservasiUiEvent: InsertReservasiUiEvent) {
        reservasiuiState = reservasiuiState.copy(insertReservasiUiEvent = insertReservasiUiEvent)
    }

    fun validateFields(): Boolean {
        val event = reservasiuiState.insertReservasiUiEvent
        Log.d("InsertReservasi", "Data to save: $event")
        val errorState = FormErrorStateReservasi(
            namaVilla = if (event.namaVilla.isNotEmpty()) null else "Nama Villa tidak boleh kosong",
            namaPelanggan = if (event.namaPelanggan.isNotEmpty()) null else "Nama Pelanggan tidak boleh kosong",
            checkIn = if (event.checkIn.isNotEmpty()) null else "Check-in tidak boleh kosong",
            checkOut = if (event.CheckOut.isNotEmpty()) null else "Check-out tidak boleh kosong",
            jumlahKamar = if (event.jumlahKamar >= 0) null else "Jumlah kamar tidak valid"
        )
        reservasiuiState = reservasiuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = reservasiuiState.insertReservasiUiEvent
        Log.d("InsertReservasi", "Data to save: $currentEvent")
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    reservasiRepository.insertReservasi(currentEvent.toReservasi())
                    reservasiuiState = reservasiuiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        insertReservasiUiEvent = InsertReservasiUiEvent(),
                        isEntryValid = FormErrorStateReservasi()
                    )
                } catch (e: Exception) {
                    Log.e("InsertVilla", "Error saving data: ${e.message}")
                    reservasiuiState = reservasiuiState.copy(
                        snackBarMessage = "Data Gagal Disimpan: ${e.message}"
                    )
                }
            }
        } else {
            reservasiuiState = reservasiuiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data yang Anda masukkan"
            )
        }
    }

    fun resetSnackBarMessage() {
        reservasiuiState = reservasiuiState.copy(snackBarMessage = null)
    }
}

data class InsertReservasiUiState(
    val insertReservasiUiEvent: InsertReservasiUiEvent = InsertReservasiUiEvent(),
    val isEntryValid: FormErrorStateReservasi = FormErrorStateReservasi(),
    val snackBarMessage: String? = null
)

data class FormErrorStateReservasi(
    val namaVilla: String? = null,
    val namaPelanggan: String? = null,
    val checkIn: String? = null,
    val checkOut: String? = null,
    val jumlahKamar: String? = null
) {
    fun isValid(): Boolean {
        return namaVilla == null && namaPelanggan == null && checkIn == null
                && checkOut == null && jumlahKamar == null
    }
}

data class InsertReservasiUiEvent(
    val namaVilla: String = "",
    val namaPelanggan: String = "",
    val checkIn: String = "",
    val CheckOut: String = "",
    val jumlahKamar: Int = 0
)

fun InsertReservasiUiEvent.toReservasi(): Reservasi = Reservasi(
    idReservasi = null,
    namaVilla = namaVilla,
    namaPelanggan = namaPelanggan,
    checkIn = checkIn,
    checkOut = CheckOut,
    jumlahKamar = jumlahKamar
)

fun Reservasi.toReservasiUiState(): InsertReservasiUiState = InsertReservasiUiState(
    insertReservasiUiEvent = toInsertReservasiUiEvent()
)

fun Reservasi.toInsertReservasiUiEvent(): InsertReservasiUiEvent = InsertReservasiUiEvent(
    namaVilla = namaVilla,
    namaPelanggan = namaPelanggan,
    checkIn = checkIn,
    CheckOut = checkOut,
    jumlahKamar = jumlahKamar
)