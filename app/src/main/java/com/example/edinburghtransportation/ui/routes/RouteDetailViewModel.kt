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

            // Önce sadece hat bilgisini yükle
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {

                val route = repository.getRouteById(routeId)

                if (route == null) {

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Hat bulunamadı."
                    )

                    return@launch
                }

                // Hat bilgisi artık hazır
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    route = route,
                    errorMessage = null,
                    isDeparturesLoading = true
                )

                // İlk durağı bul
                val firstStop = route.routeStops
                    .mapNotNull { it.stop }
                    .firstOrNull()

                if (firstStop == null) {

                    _uiState.value = _uiState.value.copy(
                        isDeparturesLoading = false
                    )

                    return@launch
                }

                // Yaklaşan seferleri ayrı olarak getir
                try {

                    val departures =
                        repository.getUpcomingDepartures(
                            routeId = routeId,
                            stopId = firstStop.id
                        )

                    _uiState.value = _uiState.value.copy(
                        isDeparturesLoading = false,
                        departures = departures
                    )

                } catch (e: Exception) {

                    e.printStackTrace()

                    _uiState.value = _uiState.value.copy(
                        isDeparturesLoading = false,
                        departures = emptyList()
                    )
                }

            } catch (e: Exception) {

                e.printStackTrace()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Hat bilgileri yüklenirken bir hata oluştu."
                )
            }
        }
    }
}