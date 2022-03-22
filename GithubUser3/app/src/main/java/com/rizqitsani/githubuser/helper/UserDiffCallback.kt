package com.rizqitsani.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.rizqitsani.githubuser.domain.models.User

class UserDiffCallback(
    private val mOldUserList: List<User>,
    private val mNewUserList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].login == mNewUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = mOldUserList[oldItemPosition]
        val newFavorite = mNewUserList[newItemPosition]
        return oldFavorite.login == newFavorite.login && oldFavorite.avatarUrl == newFavorite.avatarUrl
    }
}