package com.farrelfeno.submissionakhirbangkit.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farrelfeno.submissionakhirbangkit.database.AppExecutor
import com.farrelfeno.submissionakhirbangkit.database.FavoriteDB
import com.farrelfeno.submissionakhirbangkit.database.FavoriteUserDao
import com.farrelfeno.submissionakhirbangkit.database.FavoriteUsers
import com.farrelfeno.submissionakhirbangkit.response.DetailUsersResponse
import com.farrelfeno.submissionakhirbangkit.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DetailUsersViewModel (application: Application): ViewModel(){

    private val users = MutableLiveData<DetailUsersResponse>()
    val isFavoriteAdded = MutableLiveData<Boolean>()
    private var userDao: FavoriteUserDao?
    private var userDb: FavoriteDB?

    init {
        userDb = FavoriteDB.getDatabase(application)
        userDao = userDb?.usersDao()
    }

    val listUsers: LiveData<DetailUsersResponse> = users
    val executors = AppExecutor()



    fun setUsersDetail(query: String){
        val client = ApiConfig.getApiService().getUsersDetail(query)
        client.enqueue(object : Callback<DetailUsersResponse> {
            override fun onResponse(
                call: Call<DetailUsersResponse>,
                response: Response<DetailUsersResponse>
            ) {
                if (response.isSuccessful){
                users.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUsersResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun addUser(user: FavoriteUsers){
        executors.diskIO.execute { userDao?.addUserFavorite(user)}
    }

    fun deleteUser(user: FavoriteUsers) {
        executors.diskIO.execute{ userDao?.deleteFromUserFavorite(user)
        }
    }

    fun checkUser(usersname: String){
        executors.diskIO.execute {
            val result = userDao?.checkUserFavorite(usersname)
            isFavoriteAdded.postValue( result ==1)
        }
    }

}
