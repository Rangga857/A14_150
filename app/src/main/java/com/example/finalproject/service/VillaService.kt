package com.example.finalproject.service

import com.example.finalproject.model.Villa
import com.example.finalproject.model.VillaDetailResponse
import com.example.finalproject.model.VillaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VillaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mendapatkan daftar villa
    @GET("villa")
    suspend fun getVilla(): VillaResponse

    // Mendapatkan detail villa berdasarkan id_villa, termasuk review
    @GET("villa/{id_villa}/review")
    suspend fun getVillaById(@Path("id_villa") idVilla: String): VillaDetailResponse

    // Menambahkan villa baru
    @POST("villa/tambah")
    suspend fun insertVilla(@Body villa: Villa)

    // Mengupdate data villa berdasarkan id_villa
    @PUT("villa/{id_villa}")
    suspend fun updateVilla(@Path("id_villa") idVilla: String, @Body villa: Villa)

    // Menghapus villa berdasarkan id_villa
    @DELETE("{villa/id_villa}")
    suspend fun deleteVilla(@Path("id_villa") idVilla: String): Response<Void>
}
