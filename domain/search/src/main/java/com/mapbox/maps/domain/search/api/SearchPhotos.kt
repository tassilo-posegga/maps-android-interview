package com.mapbox.maps.domain.search.api

public interface SearchPhotos {
    public suspend operator fun invoke(searchTerm: String): List<MapPhoto>
}