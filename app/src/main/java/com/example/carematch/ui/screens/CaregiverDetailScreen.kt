package com.example.carematch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.data.model.Review
import com.example.carematch.ui.theme.CoralSecondary
import com.example.carematch.ui.theme.Slate100
import com.example.carematch.ui.theme.Slate200
import com.example.carematch.ui.theme.Slate400
import com.example.carematch.ui.theme.Slate50
import com.example.carematch.ui.theme.Slate500
import com.example.carematch.ui.theme.Slate600
import com.example.carematch.ui.theme.Slate700
import com.example.carematch.ui.theme.Slate800
import com.example.carematch.ui.theme.Slate900
import com.example.carematch.ui.theme.TealContainer
import com.example.carematch.ui.theme.TealDark
import com.example.carematch.ui.theme.TealPrimary
import com.example.carematch.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CaregiverDetailScreen(
    caregiver: CaregiverProfile?,
    reviews: List<Review>,
    onBack: () -> Unit,
    onRequestCare: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (caregiver == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Slate50),
            contentAlignment = Alignment.Center
        ) {
            Text("간병인 정보를 불러올 수 없습니다.")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${caregiver.name} 간병인 상세 프로필",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("back_button")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        bottomBar = {
            Surface(
                color = White,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("희망 일당 기준", fontSize = 12.sp, color = Slate500)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = String.format("%,d", caregiver.desiredDailyPay),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = TealPrimary
                            )
                            Text("원/일", fontSize = 13.sp, color = Slate600, modifier = Modifier.padding(bottom = 2.dp, start = 2.dp))
                        }
                    }

                    Button(
                        onClick = { onRequestCare(caregiver.profileId) },
                        modifier = Modifier
                            .height(48.dp)
                            .testTag("request_care_detail_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = White, modifier = Modifier.size(18.dp))
                            Text("1:1 지정 간병 신청", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = White)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Slate50)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(TealContainer)
                        ) {
                            if (caregiver.avatarUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = caregiver.avatarUrl,
                                    contentDescription = caregiver.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = TealDark,
                                    modifier = Modifier.size(48.dp).align(Alignment.Center)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Name & Age
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = caregiver.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Surface(
                                color = TealContainer,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "${caregiver.gender} · ${caregiver.age}세",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TealDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Location
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Slate500, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = caregiver.location,
                                fontSize = 13.sp,
                                color = Slate600
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Badges Row
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            caregiver.badges.forEach { badge ->
                                Surface(
                                    color = Slate100,
                                    shape = RoundedCornerShape(16.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(badge, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Slate800)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(color = Slate100)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Summary Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = String.format("%.2f", caregiver.rating),
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate900
                                    )
                                }
                                Text("평점 (${caregiver.reviewCount}개)", fontSize = 11.sp, color = Slate500)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${caregiver.experienceYears}년",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text("간병 경력", fontSize = 11.sp, color = Slate500)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${caregiver.completedCases}건",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text("돌봄 완료", fontSize = 11.sp, color = Slate500)
                            }
                        }
                    }
                }
            }

            // Introduction & Bio Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "간병인 소개",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = caregiver.bio,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TealDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = caregiver.detailedIntroduction,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp,
                            color = Slate700
                        )
                    }
                }
            }

            // Certifications Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "보유 국가공인 자격증",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Surface(color = TealContainer, shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = "플랫폼 검증완료",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TealDark,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        caregiver.certifications.forEach { cert ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(TealContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = TealDark, modifier = Modifier.size(16.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = cert,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Slate800
                                    )
                                    Text("보건복지부 / 공인기관 인증 완료", fontSize = 11.sp, color = Slate500)
                                }
                            }
                        }
                    }
                }
            }

            // Specialties / Skills Checklist
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "수용 가능 환자 상태 및 전문 돌봄 역량",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            caregiver.specialties.forEach { spec ->
                                Surface(
                                    color = Slate50,
                                    shape = RoundedCornerShape(10.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TealPrimary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(spec, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Slate800)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Reviews Section
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "보호자 생생 후기 (${reviews.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = String.format("%.2f", caregiver.rating),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate800
                            )
                        }
                    }
                }
            }

            if (reviews.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        Text(
                            text = "아직 등록된 후기가 없습니다. 첫 번째로 간병을 신청해 보세요!",
                            fontSize = 13.sp,
                            color = Slate500,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(reviews) { review ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Slate100)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = review.guardianName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Slate900
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(color = Slate100, shape = RoundedCornerShape(4.dp)) {
                                        Text(
                                            text = review.period,
                                            fontSize = 11.sp,
                                            color = Slate600,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(review.date, fontSize = 11.sp, color = Slate400)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(5) { index ->
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = null,
                                        tint = if (index < review.rating) Color(0xFFF59E0B) else Slate300,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "환자상태: ${review.patientCondition}",
                                    fontSize = 11.sp,
                                    color = Slate500
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = review.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Slate700,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            // Bottom Spacing
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
private val Slate300 = Color(0xFFCBD5E1)
