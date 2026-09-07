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
class RouteViewModel @Inject constructor(
    private val repository: TransitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RouteUiState())
    val uiState: StateFlow<RouteUiState> = _uiState.asStateFlow()

    init {
        loadRoutes()
    }

    fun loadRoutes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val fetchedRoutes = repository.getRoutes()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    routes = fetchedRoutes
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Hatlar yüklenirken bir sorun oluştu."
                )
            }
        }
    }
}