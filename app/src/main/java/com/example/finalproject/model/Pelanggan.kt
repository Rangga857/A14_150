package com.example.finalproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Pelanggan(
    @SerialName("id_pelanggan")
    val idPelanggan: Int? = null,

    @SerialName("nama_pelanggan")
    val namaPelanggan: String,

    @SerialName("no_hp")
    val noHp: String
)

// Respons data pelanggan tunggal
@Serializable
data class PelangganDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pelanggan
)

// Respons daftar pelanggan
@Serializable
data class PelangganResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pelanggan>
)
