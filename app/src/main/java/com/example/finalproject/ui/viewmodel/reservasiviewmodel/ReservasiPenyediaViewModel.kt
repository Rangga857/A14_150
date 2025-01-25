package com.example.finalproject.ui.viewmodel.reservasiviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.VillaApplication
import com.example.finalproject.ui.viewmodel.VillaApplication
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PagePelangganViewModel

object ReservasiPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            PageReservasiViewModel(
                VillaApplication().container.reservasiRepository
            )
        }
        initializer {
            InsertReservasiModel(
                VillaApplication().container.reservasiRepository
            )
        }
        initializer {
            DetailReservasiViewModel(
                VillaApplication().container.reservasiRepository
            )
        }
        initializer {
            EditReservasiViewModel(
                savedStateHandle = createSavedStateHandle(),
                VillaApplication().container.reservasiRepository,
            )
        }
    }
}

fun CreationExtras.VillaApplication(): VillaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplication)
