package com.example.finalproject.ui.viewmodel.reviewviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.navigation.DestinasiEditReview
import com.example.finalproject.navigation.DestinasiEditVilla
import com.example.finalproject.repository.ReviewRepository
import com.example.finalproject.repository.VillaRepository
import com.example.finalproject.ui.viewmodel.villaviewmodel.InsertVillaUiEvent
import com.example.finalproject.ui.viewmodel.villaviewmodel.InsertVillaUiState
import com.example.finalproject.ui.viewmodel.villaviewmodel.toVilla
import com.example.finalproject.ui.viewmodel.villaviewmodel.toVillaUiState
import kotlinx.coroutines.launch

class EditReviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val reviewrepository: ReviewRepository
) : ViewModel() {

    var uiReviewState by mutableStateOf(InsertReviewUiState())
        private set

    val idReview: Int = checkNotNull(savedStateHandle[DestinasiEditReview.idReview])
    init {
        viewModelScope.launch {
            try {
                val review = reviewrepository.getReviewById(idReview)
                uiReviewState = review.toReviewUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updateReviewState(
        insertReviewUiEvent: InsertReviewUiEvent
    ) {
        uiReviewState = InsertReviewUiState(insertReviewUiEvent = insertReviewUiEvent)
    }

    suspend fun updateReview() {
        viewModelScope.launch {
            try {
                val review = uiReviewState.insertReviewUiEvent.toReview()
                println("Review data being sent: $review")
                reviewrepository.updateReview(idReview, review)
            } catch (e: HttpException) {
                Log.e("UpdateReview", "HTTP error: ${e.message}")
            } catch (e: Exception) {
                Log.e("UpdateReview", "Unexpected error: ${e.localizedMessage}")
            }
        }
    }

}
