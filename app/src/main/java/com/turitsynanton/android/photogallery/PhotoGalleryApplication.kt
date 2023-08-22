package com.turitsynanton.android.photogallery

import android.app.Application

class PhotoGalleryApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesRepository.initialize(this)
    }
}