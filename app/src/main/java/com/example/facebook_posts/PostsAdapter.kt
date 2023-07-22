package com.example.facebook_posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.facebook_posts.databinding.PostItemBinding

class PostsAdapter(val posts: List<Post>) : Adapter<PostsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.personNameTv.text = post.author
        holder.binding.dateTv.text = post.date
        holder.binding.postTv.text = post.content
        holder.binding.postImg.setImageResource(post.image)
    }
}