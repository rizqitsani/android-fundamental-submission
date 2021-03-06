package com.rizqitsani.githubuser.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizqitsani.githubuser.databinding.ItemRowUserBinding
import com.rizqitsani.githubuser.domain.models.User

class ListUserAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, avatarUrl) = listUser[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(holder.binding.imgItemPhoto)

        holder.binding.tvItemUsername.text = login
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listUser.size
}