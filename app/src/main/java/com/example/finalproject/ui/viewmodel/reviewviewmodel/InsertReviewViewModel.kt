package com.example.finalproject.ui.viewmodel.reviewviewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Review
import com.example.finalproject.repository.ReviewRepository
import kotlinx.coroutines.launch

class InsertReviewViewModel(private val reviewRepository: ReviewRepository) : ViewModel() {
    var reviewuiState by mutableStateOf(InsertReviewUiState())

    fun updateInsertReviewState(insertReviewUiEvent: InsertReviewUiEvent) {
        reviewuiState = reviewuiState.copy(insertReviewUiEvent = insertReviewUiEvent)
    }

    fun validateFields(): Boolean {
        val event = reviewuiState.insertReviewUiEvent
        val errorState = FormErrorStateReview(
            Reservasi = if (event.reservasi >= 0) null else "ID Reservasi tidak boleh kosong",
            nilai = if (event.nilai.isNotEmpty()) null else "Nilai Review tidak boleh kosong",
            komentar = if (event.komentar.isNotEmpty()) null else "Komentar tidak boleh kosong",
        )
        reviewuiState = reviewuiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = reviewuiState.insertReviewUiEvent
        if (validateFields()) viewModelScope.launch {
            try {
                reviewRepository.insertReview(currentEvent.toReview())
                reviewuiState = reviewuiState.copy(
                    snackBarMessage = "Data Berhasil Disimpan",
                    insertReviewUiEvent = InsertReviewUiEvent(),
                    isEntryValid = FormErrorStateReview()
                )
            } catch (e: Exception) {
                Log.e("InsertVilla", "Error saving data: ${e.message}")
                reviewuiState = reviewuiState.copy(
                    snackBarMessage = "Data Gagal Disimpan: ${e.message}"
                )
            }
        } else {
            reviewuiState = reviewuiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data yang Anda masukkan"
            )
        }
    }

    fun resetSnackBarMessage() {
        reviewuiState = reviewuiState.copy(snackBarMessage = null)
    }
}


data class InsertReviewUiState(
    val insertReviewUiEvent: InsertReviewUiEvent = InsertReviewUiEvent(),
    val isEntryValid: FormErrorStateReview = FormErrorStateReview(),
    val snackBarMessage: String? = null
)

data class FormErrorStateReview(
    val Reservasi: String? = null,
    val nilai: String? = null,
    val komentar: String? = null
) {
    fun isValid(): Boolean {
        return Reservasi == null && nilai == null && komentar == null
    }
}

data class InsertReviewUiEvent(
    val reservasi: Int = 0,
    val nilai: String = "",
    val komentar: String = "",
)

fun InsertReviewUiEvent.toReview(): Review = Review(
    idReview = null,
    Reservasi = reservasi,
    nilai = nilai,
    komentar = komentar
)

fun Review.toReviewUiState(): InsertReviewUiState = InsertReviewUiState(
    insertReviewUiEvent = toInsertReviewUiEvent()
)

fun Review.toInsertReviewUiEvent(): InsertReviewUiEvent = InsertReviewUiEvent(
    reservasi = Reservasi,
    nilai = nilai,
    komentar = komentar,
)