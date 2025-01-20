package com.example.finalproject.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

// Send untuk entitas Villa
@Serializable
data class Villa(
    @SerialName("id_villa")
    val idVilla: String,

    @SerialName("nama_villa")
    val namaVilla: String,

    val alamat: String,

    @SerialName("kamar_tersedia")
    val kamarTersedia: Int
)

// Respons data detail Villa (termasuk review)
@Serializable
data class VillaDetailResponse(
    val status: Boolean,
    val message: String,
    val villa: Villa,
    val reviews: List<Review>
)

// Respons daftar Villa
@Serializable
data class VillaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Villa>
)





