package com.farrelfeno.submissionakhirbangkit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUsers::class], version = 5)
abstract class FavoriteDB: RoomDatabase (){
    abstract fun usersDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDB? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDB {
            if (INSTANCE == null) {
                synchronized(FavoriteDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDB::class.java, "favorite_users")
                        .build()
                }
            }
            return INSTANCE as FavoriteDB
        }
    }
}

