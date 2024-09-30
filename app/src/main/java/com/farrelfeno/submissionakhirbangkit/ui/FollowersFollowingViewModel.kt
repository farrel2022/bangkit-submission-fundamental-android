
package com.farrelfeno.submissionakhirbangkit.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farrelfeno.submissionakhirbangkit.response.ItemsItem
import com.farrelfeno.submissionakhirbangkit.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFollowingViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isonLoading: LiveData<Boolean> = _isLoading

    private val list_following = MutableLiveData<ArrayList<ItemsItem>>()
    val listFollowing: LiveData<ArrayList<ItemsItem>> = list_following

    private val list_followers = MutableLiveData<ArrayList<ItemsItem>>()
    val listFollowers: LiveData<ArrayList<ItemsItem>> = list_followers

    fun setListFollower(usersname: String){
        val client = ApiConfig.getApiService().getFollowers(usersname)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    list_followers.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun setListFollowing(usersname: String){
        val client = ApiConfig.getApiService().getFollowing(usersname)
        client.enqueue(object : Callback<ArrayList<ItemsItem>>{
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                if (response.isSuccessful){
                    list_following.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }
}