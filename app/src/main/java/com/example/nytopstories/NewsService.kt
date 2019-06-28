package com.example.nytopstories

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @get:GET("v2/science.json?api-key=ddzZFm6RyAXQc5vCB50x6ZG62SHGIvja")

    val section: Call<Website>

    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}