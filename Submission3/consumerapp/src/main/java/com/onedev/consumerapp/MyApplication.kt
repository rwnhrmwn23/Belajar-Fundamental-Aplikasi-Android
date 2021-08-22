package com.onedev.consumerapp

import android.app.Application
import com.onedev.consumerapp.di.DaggerRetroComponent
import com.onedev.consumerapp.di.RetroComponent
import com.onedev.consumerapp.di.RetroModule

class MyApplication: Application() {
    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {
        super.onCreate()

        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .build()
    }

    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }
}