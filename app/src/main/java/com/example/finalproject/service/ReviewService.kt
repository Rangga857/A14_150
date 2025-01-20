package com.example.finalproject.service

import com.example.finalproject.model.ReviewDetailResponse
import com.example.finalproject.model.ReviewRequest
import com.example.finalproject.model.ReviewResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mendapatkan daftar review
    @GET("review")
    suspend fun getReviews(): ReviewResponse

    // Mendapatkan detail review berdasarkan id_review
    @GET("review/{id_review}")
    suspend fun getReviewById(@Path("id_review") idReview: Int): ReviewDetailResponse

    // Menambahkan review baru
    @POST("review/tambah")
    suspend fun insertReview(@Body review: ReviewRequest)

    // Mengupdate data review berdasarkan id_review
    @PUT("review/{id_review}")
    suspend fun updateReview(@Path("id_review") idReview: Int, @Body review: ReviewRequest)

    // Menghapus review berdasarkan id_review
    @DELETE("review/{id_review}")
    suspend fun deleteReview(@Path("id_review") idReview: Int): Response<Void>
}
