package com.onedev.dicoding.submission_three.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.onedev.dicoding.submission_three.model.ItemUser

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(user: ItemUser)

    @Query("SELECT * FROM tb_favorite ORDER BY id ASC")
    fun selectAllFavorite(): LiveData<List<ItemUser>>

    @Query("SELECT * FROM tb_favorite WHERE login = :username")
    fun selectSpecificFavorite(username: String): ItemUser

    @Delete
    suspend fun deleteFavorite(user: ItemUser)

    @Query("DELETE FROM tb_favorite")
    suspend fun deleteAllFavorite()

}