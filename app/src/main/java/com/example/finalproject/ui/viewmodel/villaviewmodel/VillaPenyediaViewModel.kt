package com.example.finalproject.ui.viewmodel.villaviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.VillaApplication

object VillaPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            VillaDetailViewModel(
                VillaApplication().container.villaRepository
            )
        }
    }
    val FactoryInsert = viewModelFactory {
        initializer {
            InsertViewModel(
                VillaApplication().container.villaRepository
            )
        }
    }
    val FactoryUpdate = viewModelFactory {
        initializer {
            EditVillaViewModel(
                savedStateHandle = createSavedStateHandle(),
                VillaApplication().container.villaRepository
            )
        }
    }
}

fun CreationExtras.VillaApplication(): VillaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplication)