package com.example.finalproject.repository

import com.example.finalproject.model.Review
import com.example.finalproject.model.ReviewRequest
import com.example.finalproject.service.ReviewService
import java.io.IOException

interface ReviewRepository {
    suspend fun getReview(): List<Review>
    suspend fun insertReview(review: ReviewRequest)
    suspend fun updateReview(idReview: Int, review: ReviewRequest)
    suspend fun deleteReview(idReview: Int)
    suspend fun getReviewById(idReview: Int): Review
}

class NetworkReviewRepository(
    private val reviewApiService: ReviewService
) : ReviewRepository {

    override suspend fun insertReview(review: ReviewRequest) {
        reviewApiService.insertReview(review)
    }
    override suspend fun updateReview(idReview: Int, review: ReviewRequest) {
        reviewApiService.updateReview(idReview, review)
    }

    override suspend fun deleteReview(idReview: Int) {
        try {
            val response = reviewApiService.deleteReview(idReview)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Review. HTTP Status code: ${response.code()}")
                } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getReview(): List<Review> {
        return reviewApiService.getReviews().data
    }
    override suspend fun getReviewById(idReview: Int): Review {
        return reviewApiService.getReviewById(idReview).data
    }
}