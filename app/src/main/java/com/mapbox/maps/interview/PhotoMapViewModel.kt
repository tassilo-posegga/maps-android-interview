package com.mapbox.maps.interview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.maps.interview.models.MapPhoto
import com.mapbox.maps.interview.models.PhotoResponse
import com.mapbox.maps.interview.networking.WebClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import okhttp3.Request

private const val DEFAULT_SIZE_SUFFIX: String = "b"

class PhotoMapViewModel : ViewModel() {

    private val searchResponse = flow {
        // TODO fix search term and move to domain/data layer
        emit(WebClient.client.fetchImages("parks").photos.photo)
    }

    // TODO why not use StateFlow?
    val uiState: Flow<PhotoMapUiState> = searchResponse
        .mapNotNull { photos: List<PhotoResponse> ->
            val mapPhotos = photos.mapNotNull { photo ->
                // TODO move to domain/data layer
                WebClient.client.fetchLocation(photo.id)?.photo?.location?.let { location ->
                    val url =
                        "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_${DEFAULT_SIZE_SUFFIX}.jpg"
                    fetchImage(url)?.let { bitmap ->
                        MapPhoto(
                            photo.id,
                            location.latitude.toDouble(),
                            location.longitude.toDouble(),
                            bitmap
                        )
                    }
                }
            }
            PhotoMapUiState.Success(mapPhotos)
        }.stateIn(
            scope = viewModelScope,
            initialValue = PhotoMapUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    // TODO move to domain/data layer
    private suspend fun fetchImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()
        val response = WebClient.okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.source()?.readByteArray()?.let { bytes ->
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        } else {
            null
        }
    }

    private fun Bitmap.convertToMonochrome(): Bitmap {
        val paint = Paint().apply {
            val ma = ColorMatrix().apply { setSaturation(0f) }
            setColorFilter(ColorMatrixColorFilter(ma))
        }
        val bmpMonochrome = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        Canvas(bmpMonochrome).drawBitmap(this, 0F, 0F, paint)
        return bmpMonochrome
    }
}

sealed interface PhotoMapUiState {
    data object Loading : PhotoMapUiState
    data class Success(val result: List<MapPhoto>) : PhotoMapUiState
}