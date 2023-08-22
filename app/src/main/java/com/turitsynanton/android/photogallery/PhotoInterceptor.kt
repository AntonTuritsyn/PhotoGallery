package com.turitsynanton.android.photogallery

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "bbcd3596397d50f8dfd2979d9620fe5b"
class PhotoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        вызов доступа к исходному запросу
        val originalRequest: Request = chain.request()
//        создание нового запроса на основе исходного
//https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=yourApiKeyHere&format=json&nojsoncallback=1&extras=url_s
        val newUrl: HttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("extras", "url_s")
            .addQueryParameter("safesearch", "1")
            .build()
//        ерезапись исходного URL новым
        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
//        вызов chain.proceed(...) для создания ответа
        return chain.proceed(newRequest)

    }
}