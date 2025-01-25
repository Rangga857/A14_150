package com.example.finalproject.ui.viewmodel.villaviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.model.Review
import com.example.finalproject.model.Villa
import com.example.finalproject.repository.VillaRepository
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.DetailPelangganUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class VillaUiState {
    object Loading : VillaUiState()
    data class Success(val villa: Villa, val reviews: List<Review>) : VillaUiState()
    object Error : VillaUiState()
}

class VillaDetailViewModel(
    private val vla: VillaRepository) : ViewModel()
{
    private val villauiState = MutableStateFlow<VillaUiState>(VillaUiState.Loading)
    val uiState: StateFlow<VillaUiState> = villauiState

    fun getVillaById(idVilla: String) {

        viewModelScope.launch {
            villauiState.value = VillaUiState.Loading
            try {
                val (villa, reviews) = vla.getVillaById(idVilla)
                villauiState.value = VillaUiState.Success(villa, reviews)
            } catch (e: IOException) {
                e.printStackTrace()
                villauiState.value = VillaUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                villauiState.value = VillaUiState.Error
            }
        }
    }
    fun deleteVilla(idVilla: String) {
        viewModelScope.launch {
            try {
                vla.deleteVilla(idVilla)
            } catch (e: IOException) {
                villauiState.value = VillaUiState.Error
            } catch (e: retrofit2.HttpException) {
                villauiState.value = VillaUiState.Error
            }
        }
    }
}
