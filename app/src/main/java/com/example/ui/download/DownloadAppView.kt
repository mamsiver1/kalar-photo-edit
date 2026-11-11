package com.example.ui.download

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.KalarGold
import com.example.ui.theme.PhotoshopCyan
import com.example.ui.theme.PhotoshopCyanDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.AppDownloadManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DownloadAppView(
    modifier: Modifier = Modifier,
    onShowMessage: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isDownloading by remember { mutableStateOf(false) }
    var downloadProgress by remember { mutableFloatStateOf(0f) }
    var downloadSuccess by remember { mutableStateOf(false) }
    var downloadedFileName by remember { mutableStateOf("KalarEdit_v1.0.apk") }
    val apkSize = remember { AppDownloadManager.getApkFileSize(context) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Main Download Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("app_download_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, PhotoshopCyan.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                PhotoshopCyanDark.copy(alpha = 0.2f),
                                DarkSurfaceElevated
                            )
                        )
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Crest Logo
                Image(
                    painter = painterResource(id = R.drawable.kalar_logo),
                    contentDescription = "لۆگۆی کەلار ئیدت",
                    modifier = Modifier
                        .size(86.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(2.dp, KalarGold, RoundedCornerShape(18.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "داگرتنی فایلی ئەپی Kalar Edit (APK)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "دروستکراوە لەلایەن SIVER IT • وەشان: 1.0.0",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = KalarGold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "داگرتنی ڕاستەوخۆ و خێرای تەواوی ئەپڵیکەیشن بۆ ناو فۆڵدەری داونلۆدی مۆبایلەکەت، تا بتوانیت لە هەر کاتێکدا دایمەزرێنیت یان بۆ هاوڕێکانتی بنێریت بەبێ ئینتەرنێت.",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Download Progress or Action
                if (isDownloading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinearProgressIndicator(
                            progress = { downloadProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = PhotoshopCyan,
                            trackColor = DarkSurfaceBorder
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "داگرتن بۆ ناو مۆبایل... ${(downloadProgress * 100).toInt()}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PhotoshopCyan
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            isDownloading = true
                            downloadProgress = 0f
                            coroutineScope.launch {
                                // Simulate progress for smooth visual feedback
                                for (i in 1..10) {
                                    delay(60)
                                    downloadProgress = i / 10f
                                }
                                val result = AppDownloadManager.downloadApkToDevice(context)
                                isDownloading = false
                                if (result.isSuccess) {
                                    downloadSuccess = true
                                    onShowMessage(result.message)
                                } else {
                                    onShowMessage(result.message)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PhotoshopCyan),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("direct_download_apk_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            tint = DarkBg,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (downloadSuccess) "دووبارە داگرتنی ئەپ ($apkSize)" else "داگرتنی ڕاستەوخۆ بۆ مۆبایل ($apkSize)",
                            color = DarkBg,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                // If downloaded successfully, show install & share buttons
                AnimatedVisibility(visible = downloadSuccess) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF10B981).copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF10B981),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "فایلی ئەپ بە سەرکەوتوویی دابەزی!",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF10B981)
                                    )
                                    Text(
                                        text = "لە فۆڵدەری Download بەناوی $downloadedFileName پاشەکەوتکراوە.",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { AppDownloadManager.installDownloadedApk(context) },
                                colors = ButtonDefaults.buttonColors(containerColor = KalarGold),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.InstallMobile, contentDescription = null, tint = DarkBg)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("دامەزراندن ئێستا", color = DarkBg, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            Button(
                                onClick = { AppDownloadManager.shareApkFile(context) },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceElevated),
                                border = androidx.compose.foundation.BorderStroke(1.dp, PhotoshopCyan),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, tint = PhotoshopCyan)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("هاوبەشکردنی APK", color = PhotoshopCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }

        // App Information Table
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "زانیاری تەکنیکی فایلی ئەپ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PhotoshopCyan
                )

                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(icon = Icons.Default.Storage, label = "قەبارەی فایل:", value = apkSize)
                InfoRow(icon = Icons.Default.Speed, label = "وەشان (Version):", value = "1.0.0 (Release)")
                InfoRow(icon = Icons.Default.VerifiedUser, label = "دروستکەر و گەشەپێدەر:", value = "SIVER IT")
                InfoRow(icon = Icons.Default.Security, label = "پشکنینی پارێزراوی:", value = "١٠٠٪ پارێزراو و پاک (Signed APK)")
            }
        }

        // Easy Installation Guide Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ڕێنمایی چۆنیەتی دامەزراندنی ئەپ لەسەر مۆبایل",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = KalarGold
                )

                Spacer(modifier = Modifier.height(12.dp))

                StepGuideItem(
                    stepNumber = "١",
                    title = "داگرتنی فایل",
                    description = "کرتە لەسەر دوگمەی شینی 'داگرتنی ڕاستەوخۆ بۆ مۆبایل' بکە تا فایلەکە دابەزێتە فۆڵدەری Downloads."
                )

                StepGuideItem(
                    stepNumber = "٢",
                    title = "دامەزراندن (Install)",
                    description = "پاش دابەزین، دوگمەی زەردی 'دامەزراندن ئێستا' داگرە یان بڕۆ بۆ بەشی فایلی مۆبایلەکەت و کلیک لەسەر KalarEdit_v1.0.apk بکە."
                )

                StepGuideItem(
                    stepNumber = "٣",
                    title = "ڕێگەپێدانی سەرچاوە",
                    description = "ئەگەر مۆبایلەکەت پرسیاری کرد، بژاردەی (Install Unknown Apps / ڕێگەدان بەم سەرچاوەیە) چالاک بکە."
                )

                StepGuideItem(
                    stepNumber = "٤",
                    title = "دەستپێکردن",
                    description = "پاش تەواوبوون، ئەپەکە لەسەر شاشەی مۆبایلەکەت بە لۆگۆی کەلار دەکرێتەوە و ئامادەی بەکارهێنانە!"
                )
            }
        }

        // Direct Browser Link Button
        OutlinedButton(
            onClick = { AppDownloadManager.openWebDirectLink(context) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.OpenInBrowser, contentDescription = null, tint = PhotoshopCyan)
            Spacer(modifier = Modifier.width(8.dp))
            Text("کردنەوەی بەستەری داگرتن لە وێبگەڕدا", color = PhotoshopCyan)
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 13.sp, color = TextSecondary)
        }
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}

@Composable
fun StepGuideItem(
    stepNumber: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = KalarGold.copy(alpha = 0.2f),
            border = androidx.compose.foundation.BorderStroke(1.dp, KalarGold),
            modifier = Modifier.size(26.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = stepNumber,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = KalarGold
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = description, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)
        }
    }
}
