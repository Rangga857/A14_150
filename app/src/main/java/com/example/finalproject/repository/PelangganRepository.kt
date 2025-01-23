package com.example.finalproject.repository

import com.example.finalproject.model.Pelanggan
import com.example.finalproject.service.PelangganService
import java.io.IOException

interface PelangganRepository {
    suspend fun getPelanggan(): List<Pelanggan>
    suspend fun insertPelanggan(pelanggan: Pelanggan)
    suspend fun updatePelanggan(idPelanggan: Int, pelanggan: Pelanggan)
    suspend fun deletePelanggan(idPelanggan: Int)
    suspend fun getPelangganById(idPelanggan: Int): Pelanggan
}

class NetworkPelangganRepository(
    private val pelangganApiService: PelangganService
) : PelangganRepository {

    override suspend fun insertPelanggan(pelanggan: Pelanggan) {
        pelangganApiService.insertPelanggan(pelanggan)
    }

    override suspend fun updatePelanggan(idPelanggan: Int, pelanggan: Pelanggan) {

        pelangganApiService.updatePelanggan(idPelanggan, pelanggan)
    }

    override suspend fun deletePelanggan(idPelanggan: Int) {
        try {
            val response = pelangganApiService.deletePelanggan(idPelanggan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pelanggan. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPelanggan(): List<Pelanggan> {
        return pelangganApiService.getPelanggan().data
    }

    override suspend fun getPelangganById(idPelanggan: Int): Pelanggan {
        return pelangganApiService.getPelangganById(idPelanggan).data
    }
}