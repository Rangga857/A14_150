package com.example.finalproject.ui.viewmodel.reservasiviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.model.Reservasi
import com.example.finalproject.repository.ReservasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailReservasiUiState {
    object Loading : DetailReservasiUiState()
    data class Success(val reservasi: Reservasi) : DetailReservasiUiState()
    object Error : DetailReservasiUiState()
}

class DetailReservasiViewModel(private val repository: ReservasiRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailReservasiUiState>(DetailReservasiUiState.Loading)
    val uiState: StateFlow<DetailReservasiUiState> = _uiState

    fun getReservasiById(idReservasi: Int) {
        viewModelScope.launch {
            _uiState.value = DetailReservasiUiState.Loading
            try {
                val reservasi = repository.getReservasiById(idReservasi)
                _uiState.value = DetailReservasiUiState.Success(reservasi)
            } catch (e: IOException) {
                e.printStackTrace()
                _uiState.value = DetailReservasiUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _uiState.value = DetailReservasiUiState.Error
            }
        }
    }
    fun deleteReservasi(idReservasi: Int) {
        viewModelScope.launch {
            try {
                repository.deleteReservasi(idReservasi)
            } catch (e: IOException) {
                _uiState.value = DetailReservasiUiState.Error
            } catch (e: retrofit2.HttpException) {
                _uiState.value = DetailReservasiUiState.Error
            }
        }
    }
}