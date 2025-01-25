package com.example.finalproject.ui.viewmodel.reviewviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Reservasi
import com.example.finalproject.model.Review
import com.example.finalproject.repository.ReservasiRepository
import com.example.finalproject.repository.ReviewRepository
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiUiState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class PageReviewUiState{
    data class Success(val review: List<Review>) : PageReviewUiState()
    object Error : PageReviewUiState()
    object Loading : PageReviewUiState()
}

class PageReviewViewModel(private val review: ReviewRepository): ViewModel(){
    var reviewiUiState : PageReviewUiState by mutableStateOf(PageReviewUiState.Loading)
        private set

    init {
        getReview()
    }

    fun getReview() {
        viewModelScope.launch {
            reviewiUiState = PageReviewUiState.Loading
            reviewiUiState = try {
                PageReviewUiState.Success(review.getReview())
            } catch (e: IOException) {
                PageReviewUiState.Error
            }
        }
    }
}