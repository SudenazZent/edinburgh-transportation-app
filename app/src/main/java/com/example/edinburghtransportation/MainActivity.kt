package com.example.edinburghtransportation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.edinburghtransportation.ui.navigation.EdinburghNavHost
import com.example.edinburghtransportation.ui.theme.EdinburghTransportationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            EdinburghTransportationTheme {
                EdinburghNavHost()
            }
        }
    }
}