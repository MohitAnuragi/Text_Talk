package com.example.texttalk


import android.app.Application
import com.example.texttalk.data.AppContainer
import com.example.texttalk.data.AppContainerImpl


class OcrApplication: Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}