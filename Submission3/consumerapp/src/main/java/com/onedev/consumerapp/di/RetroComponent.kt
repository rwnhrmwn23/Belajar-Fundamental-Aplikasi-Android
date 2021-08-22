package com.onedev.consumerapp.di

import com.onedev.consumerapp.repository.MainRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {
    fun inject(mainRepository: MainRepository)
}