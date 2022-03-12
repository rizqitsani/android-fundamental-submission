package com.rizqitsani.githubuser.data.network.response

import com.google.gson.annotations.SerializedName

data class FollowerResponse(

	@field:SerializedName("FollowerResponse")
	val followerResponse: List<FollowerResponseItem>
)

data class FollowerResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("site_admin")
	val siteAdmin: Boolean,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("type")
	val type: String
)
