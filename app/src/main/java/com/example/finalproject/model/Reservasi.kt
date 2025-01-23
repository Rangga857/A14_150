package com.example.finalproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Reservasi(
    @SerialName("id_reservasi")
    val idReservasi: Int? = null,

    @SerialName("nama_villa")
    val namaVilla: String,

    @SerialName("nama_pelanggan")
    val namaPelanggan: String,

    @SerialName("check_in")
    val checkIn: String,

    @SerialName("check_out")
    val checkOut: String,

    @SerialName("jumlah_kamar")
    val jumlahKamar: Int
)

// Respons data detail Reservasi
@Serializable
data class ReservasiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Reservasi
)

// Respons daftar Reservasi
@Serializable
data class ReservasiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Reservasi>
)

// Model untuk membuat atau memperbarui Reservasi
/*@Serializable
data class ReservasiRequest(
    @SerialName("nama_villa")
    val namaVilla: String,

    @SerialName("nama_pelanggan")
    val namaPelanggan: String,

    @SerialName("check_in")
    val checkIn: String,

    @SerialName("check_out")
    val checkOut: String,

    @SerialName("jumlah_kamar")
    val jumlahKamar: Int
)*/

