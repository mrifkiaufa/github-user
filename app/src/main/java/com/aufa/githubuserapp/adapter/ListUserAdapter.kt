package com.aufa.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aufa.githubuserapp.activity.DetailActivity
import com.aufa.githubuserapp.data.ItemsItem
import com.aufa.githubuserapp.databinding.GithubUserRowBinding
import com.bumptech.glide.Glide

class ListUserAdapter(private val listUserResponse: List<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            GithubUserRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (login, avatarUrl) = listUserResponse[position]

        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.avatar)
        holder.binding.tvItemUsername.text = login

        holder.itemView.setOnClickListener {
            val intentToDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentToDetail.putExtra("DATA", login)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    override fun getItemCount(): Int = listUserResponse.size

    class ListViewHolder(var binding: GithubUserRowBinding) : RecyclerView.ViewHolder(binding.root)
}