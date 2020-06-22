package com.example.newssampleapp.network

import com.example.newssampleapp.network.response.NewsResponseDTO
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {
    @GET("2iodh4vg0eortkl/facts.json")
    fun getNews(): Single<List<NewsResponseDTO>>
}