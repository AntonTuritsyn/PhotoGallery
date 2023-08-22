package com.turitsynanton.android.photogallery.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrResponse(
    @Json(name = "photos") val photos: PhotoResponse
)
