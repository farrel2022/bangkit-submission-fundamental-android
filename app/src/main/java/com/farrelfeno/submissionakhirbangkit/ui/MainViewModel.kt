package com.farrelfeno.submissionakhirbangkit.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farrelfeno.submissionakhirbangkit.response.ItemsItem
import com.farrelfeno.submissionakhirbangkit.response.UsersResponse
import com.farrelfeno.submissionakhirbangkit.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel (){

    private val list_user = MutableLiveData<ArrayList<ItemsItem>>()
    val listUser: LiveData<ArrayList<ItemsItem>> = list_user

    private val _isLoading = MutableLiveData<Boolean>()
    val isonLoading: LiveData<Boolean> = _isLoading


    fun setSearchUser(query: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                list_user.value = response.body()?.items as ArrayList<ItemsItem>
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }
        })
    }
}



