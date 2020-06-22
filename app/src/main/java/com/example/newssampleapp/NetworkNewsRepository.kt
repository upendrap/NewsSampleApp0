package com.example.newssampleapp

import com.example.newssampleapp.ext.toNewsItem
import com.example.newssampleapp.network.NewsApi
import io.reactivex.Single

class NetworkNewsRepository(val newsApi: NewsApi) : NewsRepository {
    override fun fetchNews(): Single<NewsItemsWithTitle> {
        return newsApi.getNews().map {
            (NewsItemsWithTitle(
                title = it.title,
                items = it.rows.filter { it.isValid() }.map { it.toNewsItem() }))
        }
    }
}