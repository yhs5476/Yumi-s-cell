package com.example.careplus.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.ElectricBolt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careplus.ui.theme.TossBackground
import com.example.careplus.ui.theme.TossBlack
import com.example.careplus.ui.theme.TossBlue
import com.example.careplus.ui.theme.TossBlueDark
import com.example.careplus.ui.theme.TossBlueLight
import com.example.careplus.ui.theme.TossBorder
import com.example.careplus.ui.theme.TossDisabled
import com.example.careplus.ui.theme.TossGray
import com.example.careplus.ui.theme.TossGrayLight
import com.example.careplus.ui.theme.TossGreen
import com.example.careplus.ui.theme.TossGreenLight
import com.example.careplus.ui.theme.TossSurface
import kotlinx.coroutines.delay

@Composable
fun TossCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = TossSurface,
    shape: RoundedCornerShape = RoundedCornerShape(22.dp),
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = TossBlue.copy(alpha = 0.12f)),
            onClick = onClick
        )
    } else Modifier

    Surface(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = shape,
                spotColor = Color(0x0A000000),
                ambientColor = Color(0x05000000)
            )
            .clip(shape)
            .then(clickableModifier),
        shape = shape,
        color = backgroundColor
    ) {
        content()
    }
}

@Composable
fun TossButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = TossBlue,
    textColor: Color = TossSurface,
    leadingIcon: ImageVector? = null,
    testTag: String = "toss_button"
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = TossBorder,
            disabledContentColor = TossDisabled
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = if (enabled) textColor else TossDisabled
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TossSelectableCard(
    title: String,
    subtitle: String? = null,
    emoji: String? = null,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeText: String? = null,
    testTag: String = "selectable_card"
) {
    val borderColor = if (isSelected) TossBlue else TossBorder
    val bgColor = if (isSelected) TossBlueLight.copy(alpha = 0.45f) else TossSurface

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = TossBlue.copy(alpha = 0.15f)),
                onClick = onClick
            )
            .padding(horizontal = 20.dp, vertical = 18.dp)
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (emoji != null) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) TossBlue.copy(alpha = 0.15f) else TossBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                        color = if (isSelected) TossBlueDark else TossBlack
                    )
                    if (badgeText != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        TossBadge(text = badgeText, isHighlighted = isSelected)
                    }
                }
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TossGray
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) TossBlue else TossBackground)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) TossBlue else TossBorder,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = TossSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TossTagChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) TossBlue else TossBackground
    val textColor = if (isSelected) TossSurface else TossBlack
    val borderColor = if (isSelected) TossBlue else TossBorder

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = TossSurface,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = textColor
            )
        }
    }
}

@Composable
fun TossBadge(
    text: String,
    isHighlighted: Boolean = false,
    isGreen: Boolean = false
) {
    val bgColor = when {
        isGreen -> TossGreenLight
        isHighlighted -> TossBlueLight
        else -> TossBackground
    }
    val textColor = when {
        isGreen -> TossGreen
        isHighlighted -> TossBlue
        else -> TossGray
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

@Composable
fun TossTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    trailingAction: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackClick != null) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로가기",
                    tint = TossBlack
                )
            }
        } else {
            Spacer(modifier = Modifier.width(16.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TossBlack,
            modifier = Modifier.weight(1f)
        )

        if (trailingAction != null) {
            trailingAction()
        }
    }
}

@Composable
fun TossLiveTicker(
    modifier: Modifier = Modifier
) {
    val liveMessages = listOf(
        "⚡ 방금 잠실 서울아산병원에서 간병인 매칭이 완료되었어요 (14만 원/일)",
        "⚡ 삼성서울병원 70대 어르신 와상 케어 입찰 4건 접수 중",
        "⚡ 강남세브란스병원 외래 진료 동행 매칭 완료 (시간당 2.2만 원)",
        "⚡ 서울대병원 내과병동 석션 전문 케어메이트 실시간 대기 중"
    )

    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3800)
            currentIndex = (currentIndex + 1) % liveMessages.size
        }
    }

    TossCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = TossSurface,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(TossBlueLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ElectricBolt,
                    contentDescription = null,
                    tint = TossBlue,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(22.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        (fadeIn() + slideInVertically { it / 2 }).togetherWith(fadeOut() + slideOutVertically { -it / 2 })
                    },
                    label = "ticker"
                ) { index ->
                    Text(
                        text = liveMessages[index],
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = TossBlack,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
