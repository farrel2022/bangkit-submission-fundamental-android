package com.farrelfeno.submissionakhirbangkit.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUsers(
    @PrimaryKey

    @field:ColumnInfo(name = "usersname")
    val usersname: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatar_url: String
)