package com.onedev.dicoding.submission_three.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onedev.dicoding.submission_three.dao.FavoriteDao
import com.onedev.dicoding.submission_three.model.ItemUser

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val itemUser = MutableLiveData<ItemUser>()
    val selectAllFavorite: LiveData<List<ItemUser>> = favoriteDao.selectAllFavorite()

    fun selectSpecificFavorite(username: String) {
        itemUser.postValue(favoriteDao.selectSpecificFavorite(username))
    }

    suspend fun addFavorite(user: ItemUser) {
        favoriteDao.addFavorite(user)
    }

    suspend fun deleteFavorite(user: ItemUser) {
        favoriteDao.deleteFavorite(user)
    }

    suspend fun deleteAllFavorite() {
        favoriteDao.deleteAllFavorite()
    }

}