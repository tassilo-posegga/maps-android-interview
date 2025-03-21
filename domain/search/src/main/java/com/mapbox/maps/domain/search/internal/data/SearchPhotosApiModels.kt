package com.mapbox.maps.domain.search.internal.data

// TODO migrate to Kotlinx serialization and introduce domain models and mappers for clean architecture

// The outermost wrapper for the api response
internal data class PhotosSearchResponse(
    val photos: PhotosMetaData
)

internal data class PhotosMetaData(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photo: List<PhotoResponse>
)

internal data class PhotoResponse(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
)

// Models for fetching location

internal data class GetLocationResponse(
    val photo: PhotoGeoMetadata
)

internal data class PhotoGeoMetadata(
    val id: String,
    val location: LocationResponse
)

internal data class LocationResponse(
    val latitude: String,
    val longitude: String,
    val accuracy: String
)

internal data class APIErrorResponse(
    val stat: String?,
    val code: Int?,
    val message: String?
)