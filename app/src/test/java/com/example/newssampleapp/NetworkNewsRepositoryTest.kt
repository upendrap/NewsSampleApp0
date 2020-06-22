package com.example.newssampleapp

import com.example.newssampleapp.network.NewsApi
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkNewsRepositoryTest {
    lateinit var sut: NetworkNewsRepository

    @Mock
    lateinit var newsApi: NewsApi

    @Before
    fun setUp() {
        sut = NetworkNewsRepository(newsApi = newsApi)
    }

    @Test
    fun `when we fetch news they are fetched from network`() {
        val testNewsResponseDTO = testNewsResponseDTO()
        `when`(newsApi.getNews()).thenReturn(Single.just(testNewsResponseDTO))
        val testObserver = sut.fetchNews().test()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueAt(0, testNewsWithTitle())
    }

}