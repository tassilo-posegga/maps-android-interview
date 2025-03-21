package com.mapbox.maps.domain.search.internal.domain

import com.mapbox.maps.domain.search.internal.data.SearchPhotosApi
import com.mapbox.maps.domain.search.api.MapPhoto
import com.mapbox.maps.domain.search.api.SearchPhotos

private const val DEFAULT_SIZE_SUFFIX: String = "b"

internal class SearchPhotosUseCase(
    private val api: SearchPhotosApi
) : SearchPhotos {

    override suspend operator fun invoke(searchTerm: String): List<MapPhoto> =
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