package com.example.finalproject.service

import com.example.finalproject.model.ReservasiDetailResponse
import com.example.finalproject.model.ReservasiRequest
import com.example.finalproject.model.ReservasiResponse
import retrofit2.Response
import retrofit2.http.*

interface ReservasiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mendapatkan daftar reservasi
    @GET("reservasi")
    suspend fun getReservasi(): ReservasiResponse

    // Mendapatkan detail reservasi berdasarkan id_reservasi
    @GET("reservasi/{id_reservasi}")
    suspend fun getReservasiById(@Path("id_reservasi") idReservasi: Int): ReservasiDetailResponse

    // Menambahkan reservasi baru
    @POST("reservasi/tambah")
    suspend fun insertReservasi(@Body reservasi: ReservasiRequest)

    // Mengupdate data reservasi berdasarkan id_reservasi
    @PUT("reservasi/{id_reservasi}")
    suspend fun updateReservasi(@Path("id_reservasi") idReservasi: Int, @Body reservasi: ReservasiRequest)

    // Menghapus reservasi berdasarkan id_reservasi
    @DELETE("reservasi/{id_reservasi}")
    suspend fun deleteReservasi(@Path("id_reservasi") idReservasi: Int): Response<Void>
}
