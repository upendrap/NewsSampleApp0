package com.example.newssampleapp

import io.reactivex.Single
import java.net.UnknownHostException

class FakeNewsRepository : NewsRepository {
    private var responseType = RESPONSE_SUCCESS
    override fun fetchNews(): Single<NewsItemsWithTitle> {
        return if (responseType == RESPONSE_SUCCESS)
            return Single.just(testNewsWithTitle())
        else Single.error(UnknownHostException())
    }

    fun setSuccessfulResponse() {
        responseType = RESPONSE_SUCCESS
    }

    fun setErrorResponse() {
        responseType = RESPONSE_ERROR
    }

    companion object {
        const val RESPONSE_SUCCESS = 0
        const val RESPONSE_ERROR = 1
    }

    private fun testNewsWithTitle() = NewsItemsWithTitle(
        title = "News", items = listOf(
            NewsItem(
                title = "Beavers",
                description = "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
                imageUrl = "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
            ),
            NewsItem(
                title = "Flag",
                description = "",
                imageUrl = "http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png"
            ),
            NewsItem(
                title = "Eh",
                description = "A chiefly Canadian interrogative utterance, usually expressing surprise or doubt or seeking confirmation.",
                imageUrl = ""
            ),
            NewsItem(
                title = "Transportation",
                description = "It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal.",
                imageUrl = "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg"
            )
        )
    )
}
