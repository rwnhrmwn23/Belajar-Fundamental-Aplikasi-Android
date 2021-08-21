package com.onedev.dicoding.submission_three

import android.app.Application
import com.onedev.dicoding.submission_three.di.DaggerRetroComponent
import com.onedev.dicoding.submission_three.di.RetroComponent
import com.onedev.dicoding.submission_three.di.RetroModule

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