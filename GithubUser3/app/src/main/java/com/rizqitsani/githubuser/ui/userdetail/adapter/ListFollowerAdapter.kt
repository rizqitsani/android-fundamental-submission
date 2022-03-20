package com.rizqitsani.githubuser.ui.userdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizqitsani.githubuser.databinding.ItemRowUserBinding
import com.rizqitsani.githubuser.domain.models.User

class ListFollowerAdapter(private val listFollower: List<User>) :
    RecyclerView.Adapter<ListFollowerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatarUrl) = listFollower[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(holder.binding.imgItemPhoto)

        holder.binding.tvItemUsername.text = login
    }

    override fun getItemCount(): Int = listFollower.size
}