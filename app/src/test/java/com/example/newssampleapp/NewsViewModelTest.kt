package com.example.newssampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newssampleapp.archhelpers.Event
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {
    lateinit var sut: NewsViewModel

    @get:Rule
    val schedulerRule = RxSchedulerRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var newsRepository: NewsRepository

    @Mock
    lateinit var errorObserver: Observer<Event<Throwable>>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var pullToRefreshObserver: Observer<Boolean>

    @Mock
    lateinit var newsObserver: Observer<List<NewsItem>>

    @Mock
    lateinit var titleObserver: Observer<String>


    fun setup() {
        sut = NewsViewModel(newsRepository = newsRepository)
        sut.error.observeForever(errorObserver)
        sut.isLoading.observeForever(loadingObserver)
        sut.isPullDownToRefreshLoading.observeForever(pullToRefreshObserver)
        sut.news.observeForever(newsObserver)
        sut.title.observeForever(titleObserver)
    }

    fun cleanup() {
        sut.error.removeObserver(errorObserver)
        sut.isLoading.removeObserver(loadingObserver)
        sut.isPullDownToRefreshLoading.removeObserver(pullToRefreshObserver)
        sut.news.removeObserver(newsObserver)
        sut.title.removeObserver(titleObserver)
    }

    @Test
    fun `when we load news, news are fetched from news repository`() {
        val newsWithTitle = testNewsWithTitle()
        `when`(newsRepository.fetchNews()).thenReturn(Single.just(newsWithTitle))
        setup()
        verify(newsRepository).fetchNews()
        verify(newsObserver).onChanged(newsWithTitle.items)
        verify(titleObserver).onChanged(newsWithTitle.title)
        verify(loadingObserver).onChanged(false)
        verifyNoMoreInteractions(newsRepository)
        verifyNoMoreInteractions(titleObserver)
        verifyNoMoreInteractions(newsObserver)
        cleanup()
    }

    @Test
    fun `when we load news and error occures, error is shown`() {
        val exception = UnknownHostException()
        `when`(newsRepository.fetchNews()).thenReturn(Single.error(exception))
        setup()
        verify(newsRepository).fetchNews()
        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged(Event(exception))
        verifyNoMoreInteractions(newsRepository)
        verifyZeroInteractions(titleObserver)
        verifyZeroInteractions(newsObserver)
        cleanup()
    }
}