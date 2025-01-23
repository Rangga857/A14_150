package com.example.finalproject.repository

import com.example.finalproject.model.Pelanggan
import com.example.finalproject.model.Reservasi
//import com.example.finalproject.model.ReservasiRequest
import com.example.finalproject.service.PelangganService
import com.example.finalproject.service.ReservasiService
import java.io.IOException

interface ReservasiRepository {

    suspend fun getReservasi(): List<Reservasi>
    suspend fun insertReservasi(reservasi: Reservasi)
    suspend fun updateReservasi(idReservasi: Int, reservasi: Reservasi)
    suspend fun deleteReservasi(idReservasi: Int)
    suspend fun getReservasiById(idReservasi: Int): Reservasi
}

class NetworkReservasiRepository(
    private val reservasiApiService: ReservasiService
) : ReservasiRepository {

    override suspend fun insertReservasi(reservasi: Reservasi) {
        reservasiApiService.insertReservasi(reservasi)
    }
    override suspend fun updateReservasi(idReservasi: Int, reservasi: Reservasi) {
        reservasiApiService.updateReservasi(idReservasi, reservasi)
    }
    override suspend fun deleteReservasi(idReservasi: Int) {
        try {
            val response = reservasiApiService.deleteReservasi(idReservasi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Reservasi. HTTP Status code: ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
            }
    }
    override suspend fun getReservasi(): List<Reservasi> {
        return reservasiApiService.getReservasi().data
    }

    override suspend fun getReservasiById(idReservasi: Int): Reservasi {
        return reservasiApiService.getReservasiById(idReservasi).data
    }
}