package com.example.edinburghtransportation.ui.journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edinburghtransportation.data.remote.model.RouteDto
import com.example.edinburghtransportation.data.remote.model.StopDto
import com.example.edinburghtransportation.data.repository.TransitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JourneyPlannerViewModel @Inject constructor(
    private val transitRepository: TransitRepository
) : ViewModel() {

    private val _stops =
        MutableStateFlow<List<StopDto>>(emptyList())

    val stops: StateFlow<List<StopDto>> =
        _stops.asStateFlow()

    private val _selectedFromStop =
        MutableStateFlow<StopDto?>(null)

    val selectedFromStop: StateFlow<StopDto?> =
        _selectedFromStop.asStateFlow()

    private val _selectedToStop =
        MutableStateFlow<StopDto?>(null)

    val selectedToStop: StateFlow<StopDto?> =
        _selectedToStop.asStateFlow()

    private val _journeyResults =
        MutableStateFlow<List<JourneyResult>>(emptyList())

    val journeyResults: StateFlow<List<JourneyResult>> =
        _journeyResults.asStateFlow()

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    init {
        loadStops()
    }

    private fun loadStops() {

        viewModelScope.launch {

            try {

                _stops.value =
                    transitRepository.getStops()

            } catch (e: Exception) {

                println(
                    "DURAKLAR YÜKLENEMEDİ: " +
                            e.message
                )
            }
        }
    }

    fun selectFromStop(stop: StopDto) {

        _selectedFromStop.value = stop

        _errorMessage.value = null
    }

    fun selectToStop(stop: StopDto) {

        _selectedToStop.value = stop

        _errorMessage.value = null
    }

    private fun findTransferStops(
        firstRoute: RouteDto,
        secondRoute: RouteDto,
        fromStop: StopDto
    ): List<StopDto> {

        val fromIndex =
            firstRoute.routeStops.indexOfFirst {
                it.stop?.id == fromStop.id
            }

        if (fromIndex == -1) {
            return emptyList()
        }

        return firstRoute.routeStops
            .drop(fromIndex + 1)
            .mapNotNull { it.stop }
            .filter { transferStop ->

                secondRoute.routeStops.any {
                    it.stop?.id == transferStop.id
                }
            }
    }

    fun findJourney() {

        println(
            "========== FIND JOURNEY ÇALIŞTI =========="
        )

        val fromStop =
            _selectedFromStop.value

        val toStop =
            _selectedToStop.value

        // -----------------------------------------------------
        // BAŞLANGIÇ DURAĞI KONTROLÜ
        // -----------------------------------------------------

        if (fromStop == null) {

            _errorMessage.value =
                "Lütfen başlangıç noktası seçin."

            println("FROM STOP NULL")

            return
        }

        if (toStop == null) {

            _errorMessage.value =
                "Lütfen varış noktası seçin."

            println("TO STOP NULL")

            return
        }

        if (fromStop.id == toStop.id) {

            _journeyResults.value =
                emptyList()

            _errorMessage.value =
                "Başlangıç ve varış noktası aynı olamaz."

            println(
                "FROM VE TO AYNI DURAK"
            )

            return
        }


        _journeyResults.value = emptyList()

        _errorMessage.value = null

        println(
            "FROM STOP: ${fromStop.stopName} - " +
                    "ID: ${fromStop.id} - " +
                    "stopId: ${fromStop.stopId}"
        )

        println(
            "TO STOP: ${toStop.stopName} - " +
                    "ID: ${toStop.id} - " +
                    "stopId: ${toStop.stopId}"
        )

        viewModelScope.launch {

            _isLoading.value = true

            try {


                println(
                    "ROUTES GETİRİLİYOR..."
                )

                val routes =
                    transitRepository.getRoutes()

                println(
                    "TOPLAM ROUTE SAYISI: " +
                            routes.size
                )

                val fromRoutes =
                    routes.filter { route ->

                        route.routeStops.any { routeStop ->

                            routeStop.stop?.id ==
                                    fromStop.id
                        }
                    }


                val toRoutes =
                    routes.filter { route ->

                        route.routeStops.any { routeStop ->

                            routeStop.stop?.id ==
                                    toStop.id
                        }
                    }

                println(
                    "BAŞLANGIÇ HATLARI: " +
                            fromRoutes.map {
                                it.shortName
                            }
                )

                println(
                    "VARIŞ HATLARI: " +
                            toRoutes.map {
                                it.shortName
                            }
                )


                val commonRoutes =
                    routes.filter { route ->

                        val hasFromStop =
                            route.routeStops.any { routeStop ->

                                routeStop.stop?.id ==
                                        fromStop.id
                            }

                        val hasToStop =
                            route.routeStops.any { routeStop ->

                                routeStop.stop?.id ==
                                        toStop.id
                            }

                        println(
                            "ROUTE TEST: " +
                                    "${route.shortName} | " +
                                    "from=$hasFromStop | " +
                                    "to=$hasToStop"
                        )

                        hasFromStop && hasToStop
                    }

                println(
                    "COMMON ROUTES: " +
                            commonRoutes.map {
                                it.shortName
                            }
                )

                val results =
                    mutableListOf<JourneyResult>()

                commonRoutes.forEach { route ->

                    println(
                        "DİREKT SEFER ARANIYOR: " +
                                "${route.shortName}"
                    )

                    val departures =
                        transitRepository
                            .getUpcomingDepartures(
                                routeId =
                                    route.routeId,

                                stopId =
                                    fromStop.id
                            )

                    println(
                        "DEPARTURE SAYISI: " +
                                departures.size
                    )

                    val nextDeparture =
                        departures
                            .firstOrNull()
                            ?.departureTime

                    if (nextDeparture != null) {

                        results.add(
                            JourneyResult(
                                route = route,

                                nextDeparture =
                                    nextDeparture
                            )
                        )
                    }
                }

                if (results.isEmpty()) {

                    println(
                        "DİREKT HAT BULUNAMADI. " +
                                "AKTARMA ARANIYOR..."
                    )

                    for (firstRoute in fromRoutes) {

                        for (secondRoute in toRoutes) {

                            // Aynı hat ise aktarma gerekmez.
                            if (
                                firstRoute.routeId ==
                                secondRoute.routeId
                            ) {
                                continue
                            }

                            println(
                                "AKTARMA TESTİ: " +
                                        "${firstRoute.shortName} -> " +
                                        "${secondRoute.shortName}"
                            )

                            // -----------------------------------------
                            // TÜM ORTAK AKTARMA DURAKLARINI BUL
                            // -----------------------------------------

                            val transferStops =
                                findTransferStops(
                                    firstRoute =
                                        firstRoute,

                                    secondRoute =
                                        secondRoute,

                                    fromStop =
                                        fromStop
                                )

                            println(
                                "AKTARMA ADAYLARI: " +
                                        transferStops.map {
                                            it.stopName
                                        }
                            )

                            // -----------------------------------------
                            // HER AKTARMA DURAĞINI TEK TEK DENE
                            // -----------------------------------------

                            for (
                            transferStop
                            in transferStops
                            ) {

                                println(
                                    "AKTARMA DURAĞI TEST: " +
                                            "${transferStop.stopName}"
                                )

                                // -----------------------------------------
                                // İLK HATTIN SEFERİ
                                // -----------------------------------------

                                val firstDepartures =
                                    transitRepository
                                        .getUpcomingDepartures(
                                            routeId =
                                                firstRoute.routeId,

                                            stopId =
                                                fromStop.id
                                        )

                                val firstDeparture =
                                    firstDepartures
                                        .firstOrNull()
                                        ?.departureTime

                                // -----------------------------------------
                                // İKİNCİ HATTIN SEFERİ
                                // -----------------------------------------

                                val secondDepartures =
                                    transitRepository
                                        .getUpcomingDepartures(
                                            routeId =
                                                secondRoute.routeId,

                                            stopId =
                                                transferStop.id
                                        )

                                val secondDeparture =
                                    secondDepartures
                                        .firstOrNull()
                                        ?.departureTime


                                if (
                                    firstDeparture != null &&
                                    secondDeparture != null
                                ) {

                                    println(
                                        "AKTARMA BULUNDU: " +
                                                "${firstRoute.shortName} -> " +
                                                "${secondRoute.shortName} | " +
                                                "Durak: " +
                                                "${transferStop.stopName}"
                                    )

                                    results.add(
                                        JourneyResult(
                                            route =
                                                firstRoute,

                                            nextDeparture =
                                                firstDeparture,

                                            transferRoute =
                                                secondRoute,

                                            transferStop =
                                                transferStop,

                                            transferDeparture =
                                                secondDeparture
                                        )
                                    )

                                    break
                                }
                            }


                            if (results.isNotEmpty()) {
                                break
                            }
                        }

                        if (results.isNotEmpty()) {
                            break
                        }
                    }
                }



                _journeyResults.value =
                    results

                if (results.isEmpty()) {

                    _errorMessage.value =
                        "Bu iki nokta arasında uygun bir yolculuk bulunamadı."

                    println(
                        "UYGUN YOLCULUK BULUNAMADI"
                    )
                }

                println(
                    "SONUÇ SAYISI: " +
                            results.size
                )

            } catch (e: Exception) {

                println(
                    "FIND JOURNEY HATASI: " +
                            e.message
                )

                e.printStackTrace()

                _journeyResults.value =
                    emptyList()

                _errorMessage.value =
                    "Yolculuk aranırken bir hata oluştu."

            } finally {

                _isLoading.value = false
            }
        }
    }
}