package com.example.edinburghtransportation.ui.cards

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.edinburghtransportation.R

@Composable
fun AddCardScreen(
    onBackClick: () -> Unit,
    viewModel: CardsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // stringResource() çağrıları Composable context içerisinde yapılır.
    // Daha sonra bu hazır String değerlerini normal lambda'larda kullanabiliriz.
    val nfcNotSupportedText =
        stringResource(R.string.nfc_not_supported)

    val nfcNotEnabledText =
        stringResource(R.string.nfc_not_enabled)

    val nfcScanInstructionText =
        stringResource(R.string.nfc_scan_instruction)

    val cardInputRequiredText =
        stringResource(R.string.card_input_required)

    val cardNumberInvalidText =
        stringResource(R.string.card_number_invalid)

    val nfcReader = remember {
        NfcReader(context)
    }

    var cardNumber by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    var isNfcCard by remember {
        mutableStateOf(false)
    }

    DisposableEffect(Unit) {

        val activity = context as? Activity

        if (activity != null) {

            if (!nfcReader.isNfcAvailable()) {
                errorMessage = nfcNotSupportedText

            } else if (!nfcReader.isNfcEnabled()) {
                errorMessage = nfcNotEnabledText

            } else {
                nfcReader.startReading(
                    activity = activity,
                    onTagRead = { detectedCardNumber ->
                        cardNumber = detectedCardNumber
                        isNfcCard = true
                        errorMessage = null
                    }
                )
            }
        }

        onDispose {
            if (activity != null) {
                nfcReader.stopReading(activity)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = stringResource(R.string.add_card),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(R.string.add_card_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {
                errorMessage = nfcScanInstructionText
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Nfc,
                contentDescription = null
            )

            Text(
                text = " ${stringResource(R.string.read_card_with_nfc)}"
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(R.string.or),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { newValue ->

                val digitsOnly = newValue.filter {
                    it.isDigit()
                }

                if (digitsOnly.length <= 16) {
                    cardNumber = digitsOnly
                    isNfcCard = false
                    errorMessage = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.card_number)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = null
                )
            },
            singleLine = true
        )

        if (isNfcCard) {
            Text(
                text = stringResource(R.string.nfc_card_read_success),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = {

                if (cardNumber.isBlank()) {

                    errorMessage = cardInputRequiredText

                } else if (!isNfcCard && cardNumber.length != 16) {

                    errorMessage = cardNumberInvalidText

                } else {

                    viewModel.addCard(
                        cardNumber = cardNumber,
                        balance = 24.50
                    )

                    onBackClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = stringResource(R.string.add_card_button)
            )
        }
    }
}