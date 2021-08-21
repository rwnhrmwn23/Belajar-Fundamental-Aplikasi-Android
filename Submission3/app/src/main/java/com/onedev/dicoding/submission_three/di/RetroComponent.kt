package com.onedev.dicoding.submission_three.di

import com.onedev.dicoding.submission_three.repository.MainRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {
    fun inject(mainRepository: MainRepository)
}