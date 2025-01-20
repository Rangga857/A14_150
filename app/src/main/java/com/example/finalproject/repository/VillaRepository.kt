package com.example.finalproject.repository

import com.example.finalproject.model.Review
import com.example.finalproject.model.Villa
import com.example.finalproject.service.VillaService
import java.io.IOException

interface VillaRepository {
    suspend fun getVilla(): List<Villa>
    suspend fun insertVilla(villa: Villa)
    suspend fun updateVilla(idVilla: String, villa: Villa)
    suspend fun deleteVilla(idVilla: String)
    suspend fun getVillaById(idVilla: String): Pair<Villa, List<Review>>
}

class NetworkVillaRepository(
    private val villaApiService: VillaService
) : VillaRepository {

    override suspend fun insertVilla(villa: Villa) {
        villaApiService.insertVilla(villa)
    }

    override suspend fun updateVilla(idVilla: String, villa: Villa) {
        villaApiService.updateVilla(idVilla, villa)
    }
    override suspend fun deleteVilla(idVilla: String) {
        try {
            val response = villaApiService.deleteVilla(idVilla)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Villa. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getVilla(): List<Villa> {
        return villaApiService.getVilla().data
    }
    override suspend fun getVillaById(idVilla: String): Pair<Villa, List<Review>> {
        val response = villaApiService.getVillaById(idVilla)
        return Pair(response.villa, response.reviews)
    }
}
