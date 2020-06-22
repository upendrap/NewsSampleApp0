package com.example.newssampleapp.network.response

data class NewsResponseDTO(val title: String, val rows: List<NewsItemDTO>)

data class NewsItemDTO(val title: String?, val description: String?, val imageHref: String?) {
    fun isValid() = (title != null && (description != null || imageHref != null))
}
