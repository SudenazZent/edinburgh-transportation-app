package com.example.edinburghtransportation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.edinburghtransportation.R
import com.example.edinburghtransportation.ui.cards.CardsViewModel
import com.example.edinburghtransportation.ui.notifications.NotificationsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.edinburghtransportation.ui.theme.LocalAppDarkTheme
import com.example.edinburghtransportation.ui.theme.QuickActionDark
import com.example.edinburghtransportation.ui.theme.QuickActionLight

@Composable
fun HomeScreen(
    onNotificationsClick: () -> Unit,
    onCardsClick: () -> Unit,
    onRoutesClick: () -> Unit,
    onStopsClick: () -> Unit,
    onJourneyPlannerClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    val user = FirebaseAuth
        .getInstance()
        .currentUser

    val userName =
        user?.displayName
            ?: stringResource(R.string.user_default)

    val cardsViewModel: CardsViewModel = hiltViewModel()

    val cards by cardsViewModel.cards
        .collectAsStateWithLifecycle()

    val notificationsViewModel: NotificationsViewModel = hiltViewModel()

    val unreadCount by notificationsViewModel.unreadCount
        .collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Üst başlık alanı
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Ayarlar butonu
                IconButton(
                    onClick = onSettingsClick
                ) {

                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription =
                            stringResource(R.string.settings),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {

                    Text(
                        text = stringResource(R.string.hello),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "$userName 👋",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Bildirim butonu
                Box(
                    contentAlignment = Alignment.TopEnd
                ) {

                    IconButton(
                        onClick = onNotificationsClick
                    ) {

                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription =
                                stringResource(R.string.notifications),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    if (unreadCount > 0) {

                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.error,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.my_cards),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                TextButton(
                    onClick = onCardsClick
                ) {
                    Text(
                        text = stringResource(R.string.all_see),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            if (cards.isEmpty()) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surface
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = stringResource(R.string.no_cards),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = stringResource(
                                R.string.add_card_instruction
                            ),
                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

            } else {

                // Kartlar yatay kaydırılabilir
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = cards,
                        key = { it.id }
                    ) { card ->

                        Card(
                            modifier = Modifier
                                .width(310.dp)
                                .height(190.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme.colorScheme.primary
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(18.dp)
                            ) {

                                Text(
                                    text = stringResource(
                                        R.string.edinburgh
                                    ),
                                    color =
                                        MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 13.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = stringResource(
                                        R.string.edinburgh_transport_card
                                    ),
                                    color =
                                        MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                Text(
                                    text = maskCardNumber(
                                        card.cardNumber
                                    ),
                                    color =
                                        MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 16.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text = stringResource(
                                        R.string.current_balance
                                    ),
                                    color =
                                        MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 11.sp
                                )

                                Text(
                                    text = "£%.2f".format(
                                        card.balance
                                    ),
                                    color =
                                        MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 27.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )


            // Üst sıra
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                QuickActionCard(
                    title = stringResource(R.string.my_cards),
                    icon = Icons.Default.CreditCard,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    onClick = onCardsClick
                )

                QuickActionCard(
                    title = stringResource(R.string.routes),
                    icon = Icons.Default.DirectionsBus,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    onClick = onRoutesClick
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Alt sıra
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                QuickActionCard(
                    title = stringResource(R.string.stop),
                    icon = Icons.Default.LocationOn,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    onClick = onStopsClick
                )

                QuickActionCard(
                    title = stringResource(R.string.plan_journey),
                    icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    onClick = onJourneyPlannerClick
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(

            containerColor = if (LocalAppDarkTheme.current) {
                QuickActionDark
            } else {
                QuickActionLight
            }
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

private fun maskCardNumber(
    cardNumber: String
): String {

    if (cardNumber.length < 4) {
        return cardNumber
    }

    return "•••• •••• •••• " +
            cardNumber.takeLast(4)
}