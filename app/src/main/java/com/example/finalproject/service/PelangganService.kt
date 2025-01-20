package com.example.finalproject.service

import com.example.finalproject.model.PelangganDetailResponse
import com.example.finalproject.model.PelangganRequest
import com.example.finalproject.model.PelangganResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PelangganService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mendapatkan daftar pelanggan
    @GET("pelanggan")
    suspend fun getPelanggan(): PelangganResponse

    // Mendapatkan detail pelanggan berdasarkan id_pelanggan
    @GET("pelanggan/{id_pelanggan}")
    suspend fun getPelangganById(@Path("id_pelanggan") idPelanggan: Int): PelangganDetailResponse

    // Menambahkan pelanggan baru
    @POST("pelanggan/tambah")
    suspend fun insertPelanggan(@Body pelanggan: PelangganRequest)

    // Mengupdate data pelanggan berdasarkan id_pelanggan
    @PUT("pelanggan/{id_pelanggan}")
    suspend fun updatePelanggan(@Path("id_pelanggan") idPelanggan: Int, @Body pelanggan: PelangganRequest)

    // Menghapus pelanggan berdasarkan id_pelanggan
    @DELETE("pelanggan/{id_pelanggan}")
    suspend fun deletePelanggan(@Path("id_pelanggan") idPelanggan: Int): Response<Void>
}
