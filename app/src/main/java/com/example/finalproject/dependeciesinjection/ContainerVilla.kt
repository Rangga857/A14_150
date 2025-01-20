package com.example.finalproject.dependeciesinjection

import com.example.finalproject.repository.NetworkPelangganRepository
import com.example.finalproject.repository.NetworkVillaRepository
import com.example.finalproject.repository.NetworkReservasiRepository
import com.example.finalproject.repository.NetworkReviewRepository
import com.example.finalproject.repository.PelangganRepository
import com.example.finalproject.repository.VillaRepository
import com.example.finalproject.repository.ReservasiRepository
import com.example.finalproject.repository.ReviewRepository
import com.example.finalproject.service.PelangganService
import com.example.finalproject.service.VillaService
import com.example.finalproject.service.ReservasiService
import com.example.finalproject.service.ReviewService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pelangganRepository: PelangganRepository
    val villaRepository: VillaRepository
    val reservasiRepository: ReservasiRepository
    val reviewRepository: ReviewRepository
}

class ManageVillaContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    //pelanggan
    private val pelangganService: PelangganService by lazy { retrofit.create(PelangganService::class.java) }
    override val pelangganRepository: PelangganRepository by lazy { NetworkPelangganRepository(pelangganService) }

    //villa
    private val villaService: VillaService by lazy { retrofit.create(VillaService::class.java) }
    override val villaRepository: VillaRepository by lazy { NetworkVillaRepository(villaService) }

    //service
    private val reviewService: ReviewService by lazy { retrofit.create(ReviewService::class.java) }
    override val reviewRepository: ReviewRepository by lazy { NetworkReviewRepository(reviewService) }

    //reservasi
    private val reservasiService: ReservasiService by lazy { retrofit.create(ReservasiService::class.java) }
    override val reservasiRepository: ReservasiRepository by lazy { NetworkReservasiRepository(reservasiService) }
}

