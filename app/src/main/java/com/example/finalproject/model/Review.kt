package com.example.finalproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Model untuk entitas Review
@Serializable
data class Review(
    @SerialName("id_review")
    val idReview: Int? = null,

    @SerialName("id_reservasi")
    val idReservasi: Int,

    @SerialName("nilai")
    val nilai: String,

    @SerialName("komentar")
    val komentar: String
)

// Respons data detail Review
@Serializable
data class ReviewDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Review
)

// Respons daftar Review
@Serializable
data class ReviewResponse(
    val status: Boolean,
    val message: String,
    val data: List<Review>
)
