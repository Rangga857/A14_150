package com.example.finalproject.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

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

@Serializable
data class Pelanggan(
    //@SerialName("id_pelanggan")
    //val idPelanggan: Int,
    @SerialName("nama_pelanggan")
    val namaPelanggan: String,
    @SerialName("no_hp")
    val noHp: String
)

@Serializable
data class Reservasi(
    //@SerialName("id_reservasi")
    //val idReservasi: Int,
    @SerialName("id_villa")
    val idVilla: String,
    @SerialName("id_pelanggan")
    val idPelanggan: Int,
    @SerialName("check_in")
    val checkIn: String,
    @SerialName("check_out")
    val checkOut: String,
    @SerialName("jumlah_kamar")
    val jumlahKamar: Int
)

@Serializable
data class Review(
    // @SerialName("id_review")
    //val idReview: Int,
    @SerialName("id_reservasi")
    val idReservasi: Int,
    val nilai: String,
    val komentar: String
)
