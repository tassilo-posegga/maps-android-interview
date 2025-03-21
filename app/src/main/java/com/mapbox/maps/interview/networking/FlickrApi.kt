package com.mapbox.maps.interview.networking

import com.mapbox.maps.interview.models.GetLocationResponse
import com.mapbox.maps.interview.models.PhotosSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val FLICKR_API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736"

interface FlickrApi {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=$FLICKR_API_KEY")
    suspend fun fetchImages(@Query(value = "text") searchTerm: String): PhotosSearchResponse

    @GET("?method=flickr.photos.geo.getLocation&format=json&nojsoncallback=1&api_key=$FLICKR_API_KEY")
    suspend fun fetchLocation(@Query(value = "photo_id") photoId: String): GetLocationResponse?
}
