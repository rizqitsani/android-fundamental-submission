package com.rizqitsani.githubuser.data.network.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String,
)
