package com.example.edinburghtransportation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

private val ScreenPadding = 24.dp
private val GreetingNameFontSize = 28.sp

@Composable
fun HomeScreen() {

    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "Kullanıcı"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ScreenPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Merhaba,"
        )

        Text(
            text = "$userName 👋",
            fontSize = GreetingNameFontSize,
            fontWeight = FontWeight.Bold
        )
    }
}