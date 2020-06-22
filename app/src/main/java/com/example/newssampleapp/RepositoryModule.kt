package com.example.newssampleapp

import com.example.newssampleapp.network.NewsApi
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {
    @Provides
    fun provideRepository(newsApi: NewsApi): NewsRepository =
        NetworkNewsRepository(newsApi = newsApi)
}
