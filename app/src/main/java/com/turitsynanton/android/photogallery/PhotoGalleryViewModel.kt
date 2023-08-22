package com.turitsynanton.android.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turitsynanton.android.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.Query

private const val TAG = "PhotoGalleryViewModel"
class PhotoGalleryViewModel: ViewModel() {
    private val photoRepository = PhotoRepository()
    private val preferencesRepository = PreferencesRepository.get()
    private val _uiState: MutableStateFlow<PhotoGalleryUiState> = MutableStateFlow(
        PhotoGalleryUiState()
    )
    val uiState: StateFlow<PhotoGalleryUiState>
        get() = _uiState.asStateFlow()
//    Блок запускает запрос данных фотографии при первом создании ViewModel (запрос будет создан только один раз)
    init {
        viewModelScope.launch {
            preferencesRepository.storedQuery.collectLatest {storedQuery ->
                try {
//                val items = photoRepository.searchPhotos("planets")
                    val items = fetchGalleryItems(storedQuery)
//                Log.d(TAG, "Items received: $items")
                    _uiState.update { oldState ->
                        oldState.copy(
                            images = items,
                            query = storedQuery
                        )
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to fetch gallery items", e)
                }
            }
        }
    }

    fun setQuery(query: String){
        viewModelScope.launch{
            preferencesRepository.setStoredQuery(query)
        }
    }

    private suspend fun fetchGalleryItems(query: String) : List<GalleryItem> {
        return if (query.isNotEmpty()) {
            photoRepository.searchPhotos(query)
        } else {
            photoRepository.fetchPhotos()
        }
    }
}

data class PhotoGalleryUiState(
    val images: List<GalleryItem> = listOf(),
    val query: String = ""
)