package com.rizqitsani.githubuser.data.network

import com.rizqitsani.githubuser.data.network.response.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") q: String
    ): Call<SearchUserResponse>
}