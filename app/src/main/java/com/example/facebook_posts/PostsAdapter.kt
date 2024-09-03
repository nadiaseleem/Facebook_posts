package com.example.facebook_posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.facebook_posts.databinding.PostItemBinding

class PostsAdapter(private val posts: List<Post>) : Adapter<PostsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            binding.personNameTv.text = post.author
            binding.dateTv.text = post.date
            binding.postTv.text = post.content
            binding.postImg.setImageResource(post.image)
        }
    }

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
        holder.bind(post)
    }

}