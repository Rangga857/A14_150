package com.example.finalproject.ui.viewmodel.reviewviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalproject.model.Pelanggan
import com.example.finalproject.model.Review
import com.example.finalproject.repository.PelangganRepository
import com.example.finalproject.repository.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailReviewUiState {
    object Loading : DetailReviewUiState()
    data class Success(val review: Review) : DetailReviewUiState()
    object Error : DetailReviewUiState()
}

class DetailReviewViewModel(private val repositoryreview: ReviewRepository) : ViewModel() {
    private val _revivewuiState = MutableStateFlow<DetailReviewUiState>(DetailReviewUiState.Loading)
    val reviewuiState: StateFlow<DetailReviewUiState> = _revivewuiState

    fun getReviewById(idReview: Int) {
        viewModelScope.launch {
            _revivewuiState.value = DetailReviewUiState.Loading
            try {
                val review = repositoryreview.getReviewById(idReview)
                _revivewuiState.value = DetailReviewUiState.Success(review)
            } catch (e: IOException) {
                e.printStackTrace()
                _revivewuiState.value = DetailReviewUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                _revivewuiState.value = DetailReviewUiState.Error
            }
        }
    }
    fun deleteReview(idReview: Int) {
        viewModelScope.launch {
            try {
                repositoryreview.deleteReview(idReview)
            } catch (e: IOException) {
                _revivewuiState.value = DetailReviewUiState.Error
            } catch (e: retrofit2.HttpException) {
                _revivewuiState.value = DetailReviewUiState.Error
            }
        }
    }
}