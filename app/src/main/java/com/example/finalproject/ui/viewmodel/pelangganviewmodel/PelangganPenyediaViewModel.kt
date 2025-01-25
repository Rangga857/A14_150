package com.example.finalproject.ui.viewmodel.pelangganviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.VillaApplication
import com.example.finalproject.ui.viewmodel.DashboardViewModel
import com.example.finalproject.ui.viewmodel.VillaApplication

object PelangganPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            PagePelangganViewModel(
                VillaApplication().container.pelangganRepository,
                VillaApplication().container.pelangganRepository
            )
        }
        initializer {
            DetailPelangganViewModel(
                VillaApplication().container.pelangganRepository
            )
        }
        initializer {
            EditPelangganViewModel(
                savedStateHandle = createSavedStateHandle(),
                VillaApplication().container.pelangganRepository
            )
        }
    }
}

fun CreationExtras.VillaApplication():VillaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplication)