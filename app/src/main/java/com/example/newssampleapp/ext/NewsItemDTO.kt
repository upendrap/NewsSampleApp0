package com.example.newssampleapp.ext

import com.example.newssampleapp.NewsItem
import com.example.newssampleapp.network.response.NewsItemDTO

fun NewsItemDTO.toNewsItem() =
    NewsItem(title = title ?: "", description = description ?: "", imageUrl = imageHref ?: "")