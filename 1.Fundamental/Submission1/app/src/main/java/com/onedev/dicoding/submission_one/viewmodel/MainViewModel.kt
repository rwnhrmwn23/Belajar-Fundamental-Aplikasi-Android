package com.onedev.dicoding.submission_one.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.onedev.dicoding.submission_one.model.Users
import com.onedev.dicoding.submission_one.repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository(application)
    val showProgress: LiveData<Boolean>
    val usersData: LiveData<List<Users>>

    init {
        this.showProgress = repository.showProgress
        this.usersData = repository.usersData
    }

    fun loadUsersData() {
        repository.loadUsersData()
    }
}