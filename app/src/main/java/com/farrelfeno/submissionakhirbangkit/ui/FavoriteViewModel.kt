package com.farrelfeno.submissionakhirbangkit.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.farrelfeno.submissionakhirbangkit.database.FavoriteDB
import com.farrelfeno.submissionakhirbangkit.database.FavoriteUserDao
import com.farrelfeno.submissionakhirbangkit.database.FavoriteUsers

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var favoriteDao: FavoriteUserDao?
    private var favoriteDb:FavoriteDB?
    init {
        favoriteDb = FavoriteDB.getDatabase(application)
        favoriteDao = favoriteDb?.usersDao()
    }
    fun getFavoriteUsers(): LiveData<List<FavoriteUsers>>? {
        return favoriteDao?.getUserFavorite()
    }
}
