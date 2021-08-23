package com.onedev.dicoding.submission_three.dao

import android.database.Cursor
import androidx.room.*
import com.onedev.dicoding.submission_three.model.ItemUser

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(user: ItemUser?): Long

    @Query("SELECT * FROM tb_favorite ORDER BY id ASC")
    fun selectAllFavorite(): Cursor

    @Query("SELECT * FROM tb_favorite WHERE id = :id")
    fun selectSpecificFavorite(id: Int): Cursor

    @Query("DELETE FROM tb_favorite WHERE id = :id")
    fun deleteFavorite(id: Int?) : Int

    @Query("DELETE FROM tb_favorite")
    fun deleteAllFavorite() : Int

}