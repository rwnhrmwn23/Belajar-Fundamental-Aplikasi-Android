package com.onedev.dicoding.submission_three.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.onedev.dicoding.submission_three.database.FavoriteDatabase
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteRepository
    val selectAllFavorite: LiveData<List<ItemUser>>
    val itemUser: LiveData<ItemUser>

    init {
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        selectAllFavorite = repository.selectAllFavorite
        itemUser = repository.itemUser
    }

    fun selectSpecificFavorite(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectSpecificFavorite(username)
        }
    }

    fun addFavorite(user: ItemUser) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(user)
        }
    }

    fun deleteFavorite(user: ItemUser) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(user)
        }
    }

    fun deleteAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorite()
        }
    }

}