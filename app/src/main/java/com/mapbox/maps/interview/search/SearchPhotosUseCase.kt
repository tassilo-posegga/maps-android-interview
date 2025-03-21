package com.mapbox.maps.interview.search

import com.mapbox.maps.interview.models.MapPhoto

private const val DEFAULT_SIZE_SUFFIX: String = "b"

class SearchPhotosUseCase(
    private val api: SearchPhotosApi
) {
    suspend operator fun invoke(searchTerm: String): List<MapPhoto> =
        api.fetchImages(searchTerm).photos.photo.mapNotNull { photo ->
            api.fetchLocation(photo.id)?.photo?.location?.let { location ->
                val url =
                    "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_$DEFAULT_SIZE_SUFFIX.jpg"
                MapPhoto(
                    photo.id,
                    location.latitude.toDouble(),
                    location.longitude.toDouble(),
                    url
                )
            }
        }
}