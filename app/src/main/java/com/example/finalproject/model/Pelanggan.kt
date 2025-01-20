package com.example.finalproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Pelanggan(
    @SerialName("id_pelanggan")
    val idPelanggan: Int,

    @SerialName("nama_pelanggan")
    val namaPelanggan: String,

    @SerialName("no_hp")
    val noHp: String
)

//insert & edit data ke server
@Serializable
data class PelangganRequest(
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
