package com.rizqitsani.githubuser.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    var login: String = "",
    var avatarUrl: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var bio: String? = null,
    var publicRepos: Int? = null,
    var followers: Int? = null,
    var following: Int? = null,
) : Parcelable