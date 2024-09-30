package com.farrelfeno.submissionakhirbangkit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserFavorite(user: FavoriteUsers)

    @Delete
    fun deleteFromUserFavorite(id: FavoriteUsers)

    @Query("SELECT * FROM favorite_users")
    fun getUserFavorite(): LiveData<List<FavoriteUsers>>

    @Query("SELECT count(*) FROM favorite_users WHERE favorite_users.usersname = :usersname")
    fun checkUserFavorite(usersname: String): Int
}