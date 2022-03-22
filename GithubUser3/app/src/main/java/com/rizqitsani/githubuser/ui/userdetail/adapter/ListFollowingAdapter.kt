package com.rizqitsani.githubuser.ui.userdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizqitsani.githubuser.databinding.ItemRowUserBinding
import com.rizqitsani.githubuser.domain.models.User
import com.rizqitsani.githubuser.helper.UserDiffCallback

class ListFollowingAdapter : RecyclerView.Adapter<ListFollowingAdapter.ViewHolder>() {
    private val listFollowing = ArrayList<User>()
    fun setListFollowing(listFollowing: List<User>) {
        val diffCallback = UserDiffCallback(this.listFollowing, listFollowing)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFollowing.clear()
        this.listFollowing.addAll(listFollowing)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatarUrl) = listFollowing[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(holder.binding.imgItemPhoto)

        holder.binding.tvItemUsername.text = login
    }

    override fun getItemCount(): Int = listFollowing.size
}