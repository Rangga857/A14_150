package com.example.finalproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    @SerialName("id_review")
    val idReview: Int? = null,

    @SerialName("id_reservasi")
    val Reservasi: Int,

    val nilai: String,

    val komentar: String,

    @SerialName("nama_pelanggan")
    val namaPelanggan: String? = null,

    @SerialName("nama_villa")
    val namaVilla: String? = null
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
