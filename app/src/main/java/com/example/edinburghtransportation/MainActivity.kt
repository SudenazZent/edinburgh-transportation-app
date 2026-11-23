package com.example.edinburghtransportation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.edinburghtransportation.ui.navigation.EdinburghNavHost
import com.example.edinburghtransportation.ui.theme.EdinburghTransportationTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM_TOKEN", task.result)
                }
            }

        enableEdgeToEdge()

        val preferences =
            getSharedPreferences("app_settings", MODE_PRIVATE)

        setContent {

            var themeMode by remember {
                mutableStateOf(
                    preferences.getString(
                        "theme_mode",
                        "system"
                    ) ?: "system"
                )
            }

            val systemDarkTheme = isSystemInDarkTheme()

            val darkTheme = when (themeMode) {
                "dark" -> true
                "light" -> false
                else -> systemDarkTheme
            }

            EdinburghTransportationTheme(
                darkTheme = darkTheme
            ) {
                EdinburghNavHost(
                    selectedTheme = themeMode,
                    onThemeSelected = { selectedTheme ->

                        themeMode = selectedTheme

                        preferences.edit()
                            .putString(
                                "theme_mode",
                                selectedTheme
                            )
                            .apply()
                    }
                )
            }
        }
    }
}