package com.example.edinburghtransportation.ui.routes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edinburghtransportation.data.repository.TransitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteDetailViewModel @Inject constructor(
    private val repository: TransitRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(RouteDetailUiState())

    val uiState: StateFlow<RouteDetailUiState> =
        _uiState.asStateFlow()

    fun loadRoute(routeId: String) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val route = repository.getRouteById(routeId)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                route = route
            )
        }
    }
}