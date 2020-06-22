package com.example.newssampleapp

import javax.inject.Inject

class TestApp : App() {
    @Inject
    lateinit var newsRepository: NewsRepository
    override fun onCreate() {
        super.onCreate()
        DaggerTestAppComponent.builder().bindContext(this).build().inject(this)
    }
}