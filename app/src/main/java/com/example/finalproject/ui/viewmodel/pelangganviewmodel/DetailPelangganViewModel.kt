package com.example.finalproject.ui.viewmodel.pelangganviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.model.Pelanggan
import com.example.finalproject.repository.PelangganRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPelangganUiState {
    object Loading : DetailPelangganUiState()
    data class Success(val pelanggan: Pelanggan) : DetailPelangganUiState()
    object Error : DetailPelangganUiState()
}

class DetailPelangganViewModel(private val repositorypelanggan: PelangganRepository) : ViewModel() {
    private val _pelangganuiState = MutableStateFlow<DetailPelangganUiState>(DetailPelangganUiState.Loading)
    val pelangganuiState: StateFlow<DetailPelangganUiState> = _pelangganuiState

    fun getPelangganById(idpelanggan: Int) {
        viewModelScope.launch {
            _pelangganuiState.value = DetailPelangganUiState.Loading
            try {
                val pelanggan = repositorypelanggan.getPelangganById(idpelanggan)
                _pelangganuiState.value = DetailPelangganUiState.Success(pelanggan)
            } catch (e: IOException) {
                e.printStackTrace()
                _pelangganuiState.value = DetailPelangganUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _pelangganuiState.value = DetailPelangganUiState.Error
            }
        }
    }
    fun deletePelanggan(idpelanggan: Int) {
        viewModelScope.launch {
            try {
                repositorypelanggan.deletePelanggan(idpelanggan)
            } catch (e: IOException) {
                _pelangganuiState.value = DetailPelangganUiState.Error
            } catch (e: retrofit2.HttpException) {
                _pelangganuiState.value = DetailPelangganUiState.Error
            }
        }
    }
}