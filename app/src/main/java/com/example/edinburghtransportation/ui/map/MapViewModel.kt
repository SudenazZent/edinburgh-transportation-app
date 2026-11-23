package com.example.edinburghtransportation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edinburghtransportation.data.remote.model.StopDto
import com.example.edinburghtransportation.data.remote.model.VehicleDto
import com.example.edinburghtransportation.data.repository.TransitRepository
import com.example.edinburghtransportation.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val transitRepository: TransitRepository,
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _stops = MutableStateFlow<List<StopDto>>(emptyList())
    val stops: StateFlow<List<StopDto>> = _stops.asStateFlow()

    private val _vehicles = MutableStateFlow<List<VehicleDto>>(emptyList())
    val vehicles: StateFlow<List<VehicleDto>> = _vehicles.asStateFlow()

    init {
        loadStops()
        loadVehicles()
    }

    private fun loadStops() {
        viewModelScope.launch {
            _stops.value = transitRepository.getStops()
        }
    }

    private fun loadVehicles() {
        viewModelScope.launch {
            while (true) {
                try {
                    _vehicles.value = vehicleRepository.getVehicles()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                kotlinx.coroutines.delay(3000)
            }
        }
    }
}