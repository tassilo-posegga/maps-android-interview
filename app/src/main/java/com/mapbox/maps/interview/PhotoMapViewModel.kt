package com.mapbox.maps.interview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.maps.domain.search.api.MapPhoto
import com.mapbox.maps.domain.search.api.SearchPhotos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoMapViewModel(
    private val searchPhotos: SearchPhotos
) : ViewModel() {

    private val _uiState = MutableStateFlow<PhotoMapUiState>(PhotoMapUiState.Loading)
    val uiState = _uiState

    fun onSearchClicked(searchTerm: String) {
        viewModelScope.launch {
            _uiState.emit(PhotoMapUiState.Success(searchPhotos(searchTerm)))
        }
    }
}

sealed interface PhotoMapUiState {
    data object Loading : PhotoMapUiState
    data class Success(val result: List<MapPhoto>) : PhotoMapUiState
}