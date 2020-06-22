package com.example.newssampleapp

import androidx.recyclerview.widget.DiffUtil

data class NewsItemsWithTitle(val title: String, val items: List<NewsItem>)
data class NewsItem(val title: String, val description: String, val imageUrl: String)

class NewsItemDiffCallback : DiffUtil.ItemCallback<NewsItem>() {
    override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem.title == newItem.title
    }
}