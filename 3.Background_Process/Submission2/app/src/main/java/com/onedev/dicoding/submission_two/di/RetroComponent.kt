package com.onedev.dicoding.submission_two.di

import com.onedev.dicoding.submission_two.repository.MainRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {
    fun inject(mainRepository: MainRepository)
}