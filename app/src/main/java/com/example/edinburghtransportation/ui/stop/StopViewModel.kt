package com.example.edinburghtransportation.ui.stop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edinburghtransportation.data.remote.model.StopDto
import com.example.edinburghtransportation.data.repository.TransitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopsViewModel @Inject constructor(
    private val transitRepository: TransitRepository
) : ViewModel() {

    private val _stops = MutableStateFlow<List<StopDto>>(emptyList())

    val stops: StateFlow<List<StopDto>> =
        _stops.asStateFlow()

    init {
        loadStops()
    }

    private fun loadStops() {
        viewModelScope.launch {

            _stops.value =
                transitRepository.getStops()
        }
    }
}