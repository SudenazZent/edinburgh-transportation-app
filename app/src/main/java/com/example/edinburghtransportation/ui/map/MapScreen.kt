package com.example.edinburghtransportation.ui.map

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.edinburghtransportation.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {

    val stops by viewModel.stops.collectAsStateWithLifecycle()
    val vehicles by viewModel.vehicles.collectAsStateWithLifecycle()

    val edinburgh = GeoPoint(55.9533, -3.1883)

    DisposableEffect(Unit) {
        Configuration.getInstance().userAgentValue =
            "EdinburghTransportation"

        onDispose { }
    }

    AndroidView(
        factory = { context ->
            MapView(context).apply {
                setMultiTouchControls(true)

                controller.setZoom(13.0)
                controller.setCenter(edinburgh)
            }
        },

        update = { mapView ->

            mapView.overlays.removeAll { it is Marker }


            stops.forEach { stop ->

                val coordinates = stop.geometry?.coordinates

                if (coordinates != null && coordinates.size >= 2) {

                    val longitude = coordinates[0]
                    val latitude = coordinates[1]

                    val marker = Marker(mapView).apply {
                        position = GeoPoint(latitude, longitude)

                        title = stop.stopName ?: "Unknown Stop"

                        setAnchor(
                            Marker.ANCHOR_CENTER,
                            Marker.ANCHOR_BOTTOM
                        )
                    }

                    mapView.overlays.add(marker)
                }
            }

            // Mock araçları haritaya ekliyoruz
            vehicles.forEach { vehicle ->

                val marker = Marker(mapView).apply {

                    position = GeoPoint(
                        vehicle.latitude,
                        vehicle.longitude
                    )

                    title = vehicle.id

                    icon = ContextCompat.getDrawable(
                        mapView.context,
                        R.drawable.outline_directions_bus_24
                    )

                    setAnchor(
                        Marker.ANCHOR_CENTER,
                        Marker.ANCHOR_BOTTOM
                    )
                }

                mapView.overlays.add(marker)
            }

            mapView.invalidate()
        }
    )
}