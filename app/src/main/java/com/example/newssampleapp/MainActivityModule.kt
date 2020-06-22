package com.example.newssampleapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
object MainActivityModule {
    @Provides
    fun provideViewModelFactory(newsRepository: NewsRepository): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                    return NewsViewModel(
                        newsRepository = newsRepository
                    ) as T
                } else throw IllegalArgumentException()
            }
        }
}