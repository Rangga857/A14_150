package com.example.finalproject.ui.viewmodel.reservasiviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Reservasi
import com.example.finalproject.repository.ReservasiRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class PageReservasiUiState{
    data class Success(val reservasi: List<Reservasi>) : PageReservasiUiState()
    object Error : PageReservasiUiState()
    object Loading : PageReservasiUiState()
}

class PageReservasiViewModel(private val reservasi: ReservasiRepository): ViewModel(){
    var reservasiUiState : PageReservasiUiState by mutableStateOf(PageReservasiUiState.Loading)
        private set

    init {
        getReservasi()
    }

    fun getReservasi() {
        viewModelScope.launch {
            reservasiUiState = PageReservasiUiState.Loading
            reservasiUiState = try {
                PageReservasiUiState.Success(reservasi.getReservasi())
            } catch (e: IOException) {
                PageReservasiUiState.Error
            }
        }
    }
}