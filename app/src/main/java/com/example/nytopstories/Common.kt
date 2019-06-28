package com.example.nytopstories

object Common{
    val BASE_URL = "https://api.nytimes.com/svc/topstories/"
    val API_KEY = "ddzZFm6RyAXQc5vCB50x6ZG62SHGIvja"

    val newsService:NewsService
    get()= RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(section: String):String{
        val apiUrl = StringBuilder("https://api.nytimes.com/svc/topstories/")
            .append(section)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }

}