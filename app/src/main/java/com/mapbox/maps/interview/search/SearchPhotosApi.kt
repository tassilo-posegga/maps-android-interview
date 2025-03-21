package com.mapbox.maps.interview.search

import retrofit2.http.GET
import retrofit2.http.Query

const val FLICKR_API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736"

interface SearchPhotosApi {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=$FLICKR_API_KEY")
    suspend fun fetchImages(
        @Query("text") searchTerm: String,
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1
    ): PhotosSearchResponse

    @GET("?method=flickr.photos.geo.getLocation&format=json&nojsoncallback=1&api_key=$FLICKR_API_KEY")
    suspend fun fetchLocation(@Query(value = "photo_id") photoId: String): GetLocationResponse?
}
