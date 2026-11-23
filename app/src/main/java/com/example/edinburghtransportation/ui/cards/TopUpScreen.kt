package com.example.edinburghtransportation.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.edinburghtransportation.R
import com.example.edinburghtransportation.ui.theme.EdinburghYellow
import java.util.Locale
import androidx.compose.foundation.layout.statusBarsPadding
@Composable
fun TopUpScreen(
    cardId: Int,
    onBackClick: () -> Unit,
    viewModel: CardsViewModel = hiltViewModel()
) {
    var selectedAmount by remember { mutableStateOf<Double?>(null) }
    var customAmount by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val invalidAmountText =
        stringResource(R.string.invalid_amount)

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
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = stringResource(R.string.top_up_balance),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(R.string.select_amount),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AmountButton(
                amount = 5.0,
                selectedAmount = selectedAmount,
                onClick = {
                    selectedAmount = 5.0
                    customAmount = ""
                    errorMessage = null
                }
            )

            AmountButton(
                amount = 10.0,
                selectedAmount = selectedAmount,
                onClick = {
                    selectedAmount = 10.0
                    customAmount = ""
                    errorMessage = null
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AmountButton(
                amount = 20.0,
                selectedAmount = selectedAmount,
                onClick = {
                    selectedAmount = 20.0
                    customAmount = ""
                    errorMessage = null
                }
            )

            AmountButton(
                amount = 50.0,
                selectedAmount = selectedAmount,
                onClick = {
                    selectedAmount = 50.0
                    customAmount = ""
                    errorMessage = null
                }
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = customAmount,
            onValueChange = { newValue ->
                customAmount = newValue
                    .replace(",", ".")
                    .filter { it.isDigit() || it == '.' }

                selectedAmount = null
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.custom_amount)
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.custom_amount_hint)
                )
            },
            singleLine = true
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {
                val amount =
                    selectedAmount ?: customAmount.toDoubleOrNull()

                if (amount == null || amount <= 0.0) {
                    errorMessage = invalidAmountText
                } else {
                    viewModel.updateBalance(
                        cardId = cardId,
                        amount = amount
                    )

                    onBackClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(
                text = stringResource(R.string.load_balance)
            )
        }
    }
}

@Composable
private fun RowScope.AmountButton(
    amount: Double,
    selectedAmount: Double?,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selectedAmount == amount) {
                EdinburghYellow
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Text(
            text = stringResource(
                R.string.amount_format,
                String.format(Locale.UK, "%.2f", amount)
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}