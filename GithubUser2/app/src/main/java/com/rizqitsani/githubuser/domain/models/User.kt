package com.rizqitsani.githubuser.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var login: String = "",
    var avatarUrl: String? = null,
) : Parcelable