package com.example.newssampleapp

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FakeRepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(): NewsRepository =
        FakeNewsRepository()
}