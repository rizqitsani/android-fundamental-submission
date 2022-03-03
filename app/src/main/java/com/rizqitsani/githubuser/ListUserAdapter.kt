package com.rizqitsani.githubuser

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizqitsani.githubuser.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, username, avatar) = listUser[position]

        Log.d("ListUserAdapter", avatar.toString())

        holder.binding.imgItemPhoto.setImageResource(avatar)
        holder.binding.tvItemUsername.text = username
        holder.binding.tvItemName.text = name
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}