package com.example.finalproject.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.VilaApp
import com.example.finalproject.VillaApplication

object DashboardPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DashboardViewModel(
                VillaApplication().container.villaRepository
            )
        }
    }
}

fun CreationExtras.VillaApplication():VillaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplication)