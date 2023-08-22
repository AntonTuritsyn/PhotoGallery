package com.turitsynanton.android.photogallery.api

import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    /*@GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )*/
    @GET("services/rest/?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(): FlickrResponse

    /*  Аннотация @Query позволяет динамически добавлять параметр запроса, добавленный к URL-адресу.
        Здесь добавляется параметр запроса с именем text.
        Значение, присвоенное тексту, зависит от аргумента, передаваемого в searchPhotos(String).*/
    @GET("services/rest/?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String): FlickrResponse
}