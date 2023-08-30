package com.turitsynanton.android.photogallery.repository

import com.turitsynanton.android.photogallery.PhotoInterceptor
import com.turitsynanton.android.photogallery.api.FlickrApi
import com.turitsynanton.android.photogallery.api.GalleryItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PhotoRepository {
//    создание экземпляра интерфейса API
    private val flickrApi: FlickrApi
    init {
//        добавление Interceptor и настройка клиента (это заменяет клиента по умолчанию)
//        теперь Interceptor применяется ко всем сделанным запросам
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
        flickrApi = retrofit.create()
    }
    suspend fun fetchPhotos(): List<GalleryItem> = flickrApi.fetchPhotos().photos.galleryItems

    suspend fun searchPhotos(query: String): List<GalleryItem> = flickrApi.searchPhotos(query).photos.galleryItems
}