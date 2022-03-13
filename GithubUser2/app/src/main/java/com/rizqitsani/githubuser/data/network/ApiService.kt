package com.rizqitsani.githubuser.data.network

import com.rizqitsani.githubuser.data.network.response.FollowerResponse
import com.rizqitsani.githubuser.data.network.response.FollowingResponse
import com.rizqitsani.githubuser.data.network.response.SearchUserResponse
import com.rizqitsani.githubuser.data.network.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q") q: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowerResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponse>>
}