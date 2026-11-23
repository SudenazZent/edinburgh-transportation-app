package com.example.edinburghtransportation.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edinburghtransportation.R

data class FaqItem(
    val question: String,
    val answer: String
)

@Composable
private fun getFaqItems(): List<FaqItem> {
    return listOf(
        FaqItem(
            question = stringResource(R.string.faq_login_question),
            answer = stringResource(R.string.faq_login_answer)
        ),
        FaqItem(
            question = stringResource(R.string.faq_add_card_question),
            answer = stringResource(R.string.faq_add_card_answer)
        ),
        FaqItem(
            question = stringResource(R.string.faq_top_up_question),
            answer = stringResource(R.string.faq_top_up_answer)
        ),
        FaqItem(
            question = stringResource(R.string.faq_routes_question),
            answer = stringResource(R.string.faq_routes_answer)
        ),
        FaqItem(
            question = stringResource(R.string.faq_stops_question),
            answer = stringResource(R.string.faq_stops_answer)
        ),
        FaqItem(
            question = stringResource(R.string.faq_journey_question),
            answer = stringResource(R.string.faq_journey_answer)
        ),
        FaqItem(
            question = stringResource(R.string.faq_notifications_question),
            answer = stringResource(R.string.faq_notifications_answer)
        ),
        FaqItem(
            question = stringResource(R.string.faq_share_question),
            answer = stringResource(R.string.faq_share_answer)
        )
    )
}

@Composable
fun FaqScreen(
    onBackClick: () -> Unit = {}
) {
    val faqItems = getFaqItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        item {
            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
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
                    text = stringResource(R.string.faq_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = stringResource(R.string.help),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        items(faqItems) { faqItem ->
            FaqRow(
                faqItem = faqItem
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}

@Composable
private fun FaqRow(
    faqItem: FaqItem
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = faqItem.question,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Icon(
                    imageVector =
                        if (isExpanded) {
                            Icons.Default.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                    contentDescription =
                        if (isExpanded) {
                            stringResource(R.string.faq_close_answer)
                        } else {
                            stringResource(R.string.faq_open_answer)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            if (isExpanded) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = faqItem.answer,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
            }
        }
    }
}