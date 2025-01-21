package com.example.finalproject.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.model.Villa
import com.example.finalproject.repository.VillaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DashboardUiState{
    data class Success(val mahasiswa: List<Villa>) : DashboardUiState()
    object Error : DashboardUiState()
    object Loading : DashboardUiState()
}

class DashboardViewModel(private val villa: VillaRepository): ViewModel(){
    var villaUiState : DashboardUiState by mutableStateOf(DashboardUiState.Loading)
        private set

    init {
        getVilla()
    }

    fun getVilla() {
        viewModelScope.launch {
            villaUiState = DashboardUiState.Loading
            villaUiState = try {
                DashboardUiState.Success(villa.getVilla())
            } catch (e: IOException) {
                DashboardUiState.Error
            }
        }
    }
}