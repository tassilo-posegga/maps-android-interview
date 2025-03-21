package com.mapbox.maps.interview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.maps.interview.models.MapPhoto
import com.mapbox.maps.interview.search.SearchPhotosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoMapViewModel(
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PhotoMapUiState>(PhotoMapUiState.Loading)
    val uiState = _uiState

    fun onSearchClicked(searchTerm: String) {
        viewModelScope.launch {
            _uiState.emit(PhotoMapUiState.Success(searchPhotosUseCase(searchTerm)))
        }
    }
}

sealed interface PhotoMapUiState {
    data object Loading : PhotoMapUiState
    data class Success(val result: List<MapPhoto>) : PhotoMapUiState
}