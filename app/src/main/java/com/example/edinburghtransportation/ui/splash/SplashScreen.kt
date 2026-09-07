package com.example.edinburghtransportation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val SplashBackgroundColor = Color(0xFF1F4A85)
private val ProgressTrackColor = Color(0xFF9DB5D6)
private val ProgressFillColor = Color(0xFFFFC400)
private const val SPLASH_DURATION_MS = 2000L

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {

    LaunchedEffect(Unit) {
        delay(SPLASH_DURATION_MS)
        onSplashFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackgroundColor)
            .padding(horizontal = 48.dp)
            .padding(top = 180.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "🏰",
            fontSize = 64.sp
        )

        Text(
            text = "Edinburgh\nTransportation",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )

        Text(
            text = "Toplu taşımayı kolayca yönetin.",
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .padding(top = 16.dp)
                .background(ProgressTrackColor, RoundedCornerShape(10.dp))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(6.dp)
                    .background(ProgressFillColor, RoundedCornerShape(10.dp))
            )
        }

        Text(
            text = "Yükleniyor...",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}