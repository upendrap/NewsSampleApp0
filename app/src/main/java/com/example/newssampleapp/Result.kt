package com.example.newssampleapp

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Failure<T>(val exception: Throwable) : Result<T>()
}