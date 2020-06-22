package com.example.newssampleapp

import io.reactivex.Single

interface NewsRepository {
    fun fetchNews(): Single<Result<List<NewsItem>>>
}