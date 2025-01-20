package com.example.finalproject

import android.app.Application
import com.example.finalproject.dependeciesinjection.AppContainer
import com.example.finalproject.dependeciesinjection.ManageVillaContainer

class VillaApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = ManageVillaContainer()
    }
}