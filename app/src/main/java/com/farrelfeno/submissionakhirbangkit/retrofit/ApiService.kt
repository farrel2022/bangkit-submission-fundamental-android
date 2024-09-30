package com.farrelfeno.submissionakhirbangkit.retrofit

import com.farrelfeno.submissionakhirbangkit.BuildConfig
import com.farrelfeno.submissionakhirbangkit.response.DetailUsersResponse
import com.farrelfeno.submissionakhirbangkit.response.ItemsItem
import com.farrelfeno.submissionakhirbangkit.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query : String
    ): Call<UsersResponse>
    @GET("users/{username}")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<DetailUsersResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}