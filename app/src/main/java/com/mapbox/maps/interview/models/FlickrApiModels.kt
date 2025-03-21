package com.mapbox.maps.interview.models

// The outermost wrapper for the api response
data class PhotosSearchResponse(
    val photos: PhotosMetaData
)

data class PhotosMetaData(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photo: List<PhotoResponse>
)

data class PhotoResponse(
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

data class GetLocationResponse(
    val photo: PhotoGeoMetadata
)

data class PhotoGeoMetadata(
    val id: String,
    val location: LocationResponse
)

data class LocationResponse(
    val latitude: String,
    val longitude: String,
    val accuracy: String
)

data class APIErrorResponse(
    val stat: String?,
    val code: Int?,
    val message: String?
)