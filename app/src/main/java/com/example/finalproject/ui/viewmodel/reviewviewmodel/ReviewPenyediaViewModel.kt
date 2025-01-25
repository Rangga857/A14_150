package com.example.finalproject.ui.viewmodel.reviewviewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.VillaApplication
import com.example.finalproject.ui.view.review.InsertReviewView
import com.example.finalproject.ui.viewmodel.VillaApplication
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiViewModel

object ReviewPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            PageReviewViewModel(
                VillaApplication().container.reviewRepository
            )
        }
        initializer {
            InsertReviewViewModel(
                VillaApplication().container.reviewRepository
            )
        }
        initializer {
            DetailReviewViewModel(
                VillaApplication().container.reviewRepository
            )
        }
        initializer {
            EditReviewViewModel(
                savedStateHandle = createSavedStateHandle(),
                VillaApplication().container.reviewRepository
            )
        }
    }
}

fun CreationExtras.VillaApplication(): VillaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplication)