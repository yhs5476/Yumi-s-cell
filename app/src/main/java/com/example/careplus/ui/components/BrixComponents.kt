package com.example.careplus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careplus.data.model.getBrixInfo
import com.example.careplus.ui.theme.TossBlack
import com.example.careplus.ui.theme.TossBorder
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossSurface

@Composable
fun BrixGaugeBadge(
    brixScore: Float,
    modifier: Modifier = Modifier,
    showDetails: Boolean = true
) {
    val brixInfo = getBrixInfo(brixScore)
    val badgeColor = Color(brixInfo.badgeColorHex)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(badgeColor.copy(alpha = 0.08f))
            .border(1.dp, badgeColor.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = brixInfo.emoji,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "${String.format("%.1f", brixInfo.brixScore)} °Brix",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = badgeColor
            )

            if (showDetails) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeColor)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = brixInfo.tierName,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BrixProgressBar(
    brixScore: Float,
    modifier: Modifier = Modifier
) {
    val brixInfo = getBrixInfo(brixScore)
    val mainColor = Color(brixInfo.badgeColorHex)
    val progress = (brixScore / 24.0f).coerceIn(0.05f, 1.0f)

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${brixInfo.emoji} 신뢰 당도",
                    style = MaterialTheme.typography.bodySmall,
                    color = TossGray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = brixInfo.tierName,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = mainColor
                )
            }
            Text(
                text = "${String.format("%.1f", brixScore)} / 24.0 °Brix",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = TossBlack
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Gauge track & fill
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(TossBorder)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(mainColor)
            )
        }
    }
}
