package com.rizqitsani.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE login = :login")
    suspend fun delete(login: String)

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE login = :login LIMIT 1")
    fun getFavoriteByLogin(login: String): LiveData<Favorite>
}