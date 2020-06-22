package com.example.newssampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newssampleapp.databinding.CellNewsBinding

class NewsAdapter(diffCallback: DiffUtil.ItemCallback<NewsItem>) :
    ListAdapter<NewsItem, NewsViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = CellNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding = binding, view = binding.root)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NewsViewHolder(val binding: CellNewsBinding, view: View) : RecyclerView.ViewHolder(view) {
    fun bind(newsItem: NewsItem) {
        with(binding) {
            tvTitle.text = newsItem.title
            tvDescription.text = newsItem.description
            Glide.with(itemView).load(newsItem.imageUrl).placeholder(R.drawable.ic_news_placeholder)
                .error(R.drawable.ic_news_placeholder).into(ivIcon)
        }
    }
}