package com.example.newssampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newssampleapp.archhelpers.Event
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel(), UserActions {
    private val compositeDisposable = CompositeDisposable()
    private val _news = MutableLiveData<List<NewsItem>>()
    val news: LiveData<List<NewsItem>> = _news
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isPullToRefreshLoading = MutableLiveData<Boolean>(false)
    val isPullDownToRefreshLoading: LiveData<Boolean> = _isPullToRefreshLoading
    private val _error = MutableLiveData<Event<Throwable>>()
    val error: LiveData<Event<Throwable>> = _error

    init {
        load(refresh = false)
    }

    override fun load(refresh: Boolean) {
        newsRepository.fetchNews().subscribeOn(io()).observeOn(mainThread())
            .doOnSubscribe {
                if (refresh)
                    _isPullToRefreshLoading.postValue(true)
                else
                    _isLoading.postValue(true)
            }
            .doFinally {
                if (refresh)
                    _isPullToRefreshLoading.postValue(false)
                else
                    _isLoading.postValue(false)
            }
            .subscribe(::handleList, ::handleError).also { compositeDisposable.add(it) }
    }

    private fun handleList(items: NewsItemsWithTitle) {
        _news.postValue(items.items)
        _title.postValue(items.title)
    }

    private fun handleError(throwable: Throwable) {
        _error.postValue(Event(throwable))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

interface UserActions {
    fun load(refresh: Boolean)
}