package com.onedev.dicoding.submission_two

import android.app.Application
import com.onedev.dicoding.submission_two.di.DaggerRetroComponent
import com.onedev.dicoding.submission_two.di.RetroComponent
import com.onedev.dicoding.submission_two.di.RetroModule

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