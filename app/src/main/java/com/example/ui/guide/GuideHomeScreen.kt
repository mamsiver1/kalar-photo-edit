package com.example.ui.guide

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.FormatColorReset
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.ui.download.DownloadAppView
import com.example.util.AppDownloadManager
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.PhotoshopGuideData
import com.example.model.AdjustmentConcept
import com.example.model.DesignConcept
import com.example.model.FilterConcept
import com.example.model.FormatItem
import com.example.model.GuideSection
import com.example.model.LayerConcept
import com.example.model.PanelItem
import com.example.model.ToolItem
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.KalarGold
import com.example.ui.theme.KalarGoldDark
import com.example.ui.theme.PhotoshopCyan
import com.example.ui.theme.PhotoshopCyanDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideHomeScreen(
    onOpenEditor: (toolId: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSection by remember { mutableStateOf(GuideSection.OVERVIEW) }
    var selectedItemDetail by remember { mutableStateOf<Any?>(null) }
    var isQuizOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val triggerDownloadAction: () -> Unit = {
        coroutineScope.launch {
            val res = AppDownloadManager.downloadApkToDevice(context)
            snackbarHostState.showSnackbar(res.message)
            if (res.isSuccess) {
                AppDownloadManager.installDownloadedApk(context)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBg,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Surface(
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "دروستکراوە لەلایەن SIVER IT",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = KalarGold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "kalar edit • ستۆدیۆ و ڕێبەری سەرەکی فۆتۆشۆپ",
                        fontSize = 10.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Header with Castle Crest Logo
            item {
                HeroHeaderSection(
                    onOpenEditor = { onOpenEditor(null) },
                    onOpenQuiz = { isQuizOpen = true },
                    onDownloadApp = triggerDownloadAction,
                    onNavigateToDownload = { selectedSection = GuideSection.DOWNLOAD }
                )
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("گەڕان بەدوای ئامراز، چین، ڕەنگ، فلتەر یان کورتەپیت...", fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "گەڕان", tint = PhotoshopCyan)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "سڕینەوە", tint = TextSecondary)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_field"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceElevated,
                        unfocusedContainerColor = DarkSurface,
                        focusedBorderColor = PhotoshopCyan,
                        unfocusedBorderColor = DarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )
            }

            // Category Tab Selector
            item {
                CategoryTabsRow(
                    selectedSection = selectedSection,
                    onSectionSelected = { selectedSection = it }
                )
            }

            // Content Sections based on selection or search
            if (searchQuery.isNotBlank()) {
                // Search Results
                item {
                    SearchResultsView(
                        query = searchQuery,
                        onItemClick = { selectedItemDetail = it },
                        onOpenEditor = onOpenEditor
                    )
                }
            } else {
                when (selectedSection) {
                    GuideSection.OVERVIEW -> {
                        item {
                            OverviewSection(
                                onNavigateToSection = { selectedSection = it },
                                onOpenEditor = { onOpenEditor(null) }
                            )
                        }
                    }
                    GuideSection.PANELS -> {
                        item {
                            SectionHeader(
                                title = "١. بەشە سەرەکییەکانی Photoshop",
                                subtitle = "١٠ بەش و پەنجەرەی بنەڕەتی ڕووکاری کارکردنی فۆتۆشۆپ"
                            )
                        }
                        items(PhotoshopGuideData.panels) { panel ->
                            PanelCard(panel = panel, onClick = { selectedItemDetail = panel })
                        }
                    }
                    GuideSection.TOOLBAR -> {
                        item {
                            SectionHeader(
                                title = "٢. ئامرازەکانی Toolbar",
                                subtitle = "٢١ ئامرازی سەرەکی دەستکاریکردن، بڕین و دیزاین بە کوردی"
                            )
                        }
                        items(PhotoshopGuideData.tools) { tool ->
                            ToolCard(
                                tool = tool,
                                onClick = { selectedItemDetail = tool },
                                onTryInEditor = { onOpenEditor(tool.id) }
                            )
                        }
                    }
                    GuideSection.LAYERS -> {
                        item {
                            SectionHeader(
                                title = "٣. بەشەکانی Layers",
                                subtitle = "بەڕێوەبردنی چینەکان، شەفافییەت، ماسک و دۆخی تێکەڵبوون"
                            )
                        }
                        items(PhotoshopGuideData.layers) { layer ->
                            LayerConceptCard(concept = layer, onClick = { selectedItemDetail = layer })
                        }
                    }
                    GuideSection.ADJUSTMENTS -> {
                        item {
                            SectionHeader(
                                title = "٤. دەستکاریکردنی ڕەنگ و وێنە",
                                subtitle = "١٢ ئامرازی جیهانی بۆ گۆڕینی ڕووناکی، کۆنتراست و هاوسەنگی ڕەنگ"
                            )
                        }
                        items(PhotoshopGuideData.adjustments) { adj ->
                            AdjustmentCard(concept = adj, onClick = { selectedItemDetail = adj })
                        }
                    }
                    GuideSection.FILTERS -> {
                        item {
                            SectionHeader(
                                title = "٥. بەشەکانی فلتەر و کاریگەرییەکان",
                                subtitle = "کاریگەرییە هونەرییەکان، نەرمکردن، تیژکردن و فلتەرەکانی AI"
                            )
                        }
                        items(PhotoshopGuideData.filters) { filter ->
                            FilterCard(concept = filter, onClick = { selectedItemDetail = filter })
                        }
                    }
                    GuideSection.DESIGN -> {
                        item {
                            SectionHeader(
                                title = "٦. بەشەکانی نووسین و دیزاین",
                                subtitle = "فۆنت، پاراگراف، شێوە ئەندازیارییەکان، ڕێڕەو و نەخشەکان"
                            )
                        }
                        items(PhotoshopGuideData.designs) { design ->
                            DesignCard(concept = design, onClick = { selectedItemDetail = design })
                        }
                    }
                    GuideSection.FORMATS -> {
                        item {
                            SectionHeader(
                                title = "٧. فۆرماتەکانی پاشەکەوتکردن",
                                subtitle = "جیاوازی و تایبەتمەندی فۆرماتەکانی PSD, PNG, JPG, TIFF, PDF, GIF"
                            )
                        }
                        items(PhotoshopGuideData.formats) { format ->
                            FormatCard(format = format, onClick = { selectedItemDetail = format })
                        }
                    }
                    GuideSection.DOWNLOAD -> {
                        item {
                            DownloadAppView(
                                onShowMessage = { msg ->
                                    coroutineScope.launch { snackbarHostState.showSnackbar(msg) }
                                }
                            )
                        }
                    }
                    GuideSection.QUIZ -> {
                        item {
                            QuizInteractiveView()
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    // Item Detail Bottom Sheet
    selectedItemDetail?.let { item ->
        DetailBottomSheet(
            item = item,
            onDismiss = { selectedItemDetail = null },
            onOpenEditor = { toolId ->
                selectedItemDetail = null
                onOpenEditor(toolId)
            }
        )
    }

    // Quiz Dialog
    if (isQuizOpen) {
        QuizModalSheet(onDismiss = { isQuizOpen = false })
    }
}

@Composable
fun HeroHeaderSection(
    onOpenEditor: () -> Unit,
    onOpenQuiz: () -> Unit,
    onDownloadApp: () -> Unit,
    onNavigateToDownload: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hero_header_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, KalarGold.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            PhotoshopCyanDark.copy(alpha = 0.25f),
                            DarkSurfaceElevated
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Uploaded Kalar Crest Logo
                Image(
                    painter = painterResource(id = R.drawable.kalar_logo),
                    contentDescription = "لۆگۆی کەلار ئیدت",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, KalarGold, RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Kalar Edit",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "کەلار ئیدت • ڕێبەری تەواوی فۆتۆشۆپ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PhotoshopCyan
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "دروستکراوە لەلایەن SIVER IT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = KalarGold
                    )
                }

                // Direct Download Quick Action Icon Button
                Surface(
                    shape = CircleShape,
                    color = PhotoshopCyan,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onDownloadApp)
                        .testTag("hero_direct_download_icon")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "داگرتنی ڕاستەوخۆ بۆ مۆبایل",
                            tint = DarkBg,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "ئەپڵیکەیشنی فەرمی دەستکاریکردنی وێنە و گەورەترین ڕێبەری پەروەردەیی بۆ فێربوونی هەموو ئامراز، بەش و فلتەرەکانی ئەدۆبی فۆتۆشۆپ بە زمانی شیرینی کوردی.",
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons Row (Studio, Quiz, Download)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onOpenEditor,
                    colors = ButtonDefaults.buttonColors(containerColor = PhotoshopCyan),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.1f)
                        .testTag("launch_editor_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = DarkBg,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "ستۆدیۆ",
                        color = DarkBg,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = onDownloadApp,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.2f)
                        .testTag("hero_download_app_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = DarkBg,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "داگرتنی ئەپ",
                        color = DarkBg,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = onOpenQuiz,
                    colors = ButtonDefaults.buttonColors(containerColor = KalarGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.1f)
                        .testTag("launch_quiz_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        tint = DarkBg,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "تاقیکردنەوە",
                        color = DarkBg,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryTabsRow(
    selectedSection: GuideSection,
    onSectionSelected: (GuideSection) -> Unit
) {
    val sections = listOf(
        GuideSection.OVERVIEW to Icons.Default.Dashboard,
        GuideSection.PANELS to Icons.Default.Category,
        GuideSection.TOOLBAR to Icons.Default.Brush,
        GuideSection.LAYERS to Icons.Default.Layers,
        GuideSection.ADJUSTMENTS to Icons.Default.Tune,
        GuideSection.FILTERS to Icons.Default.AutoFixHigh,
        GuideSection.DESIGN to Icons.Default.TextFields,
        GuideSection.FORMATS to Icons.Default.Save,
        GuideSection.DOWNLOAD to Icons.Default.Download,
        GuideSection.QUIZ to Icons.Default.Quiz
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(sections) { (section, icon) ->
            val isSelected = selectedSection == section
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) PhotoshopCyan else DarkSurfaceElevated,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) PhotoshopCyan else DarkSurfaceBorder
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onSectionSelected(section) }
                    .testTag("tab_${section.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = section.titleKurdish,
                        tint = if (isSelected) DarkBg else PhotoshopCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = section.titleKurdish,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) DarkBg else TextPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = PhotoshopCyan
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = TextSecondary
        )
    }
}

@Composable
fun OverviewSection(
    onNavigateToSection: (GuideSection) -> Unit,
    onOpenEditor: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "٧ کۆڵەکەی سەرەکی فۆتۆشۆپ بە زمانی کوردی:",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        val quickLinks = listOf(
            Triple("١. بەشە سەرەکییەکان", "Menu, Toolbar, Layers, Properties, Navigator", GuideSection.PANELS),
            Triple("٢. ئامرازەکانی Toolbar", "Move, Marquee, Lasso, Brush, Eraser, Pen, Zoom", GuideSection.TOOLBAR),
            Triple("٣. بەشەکانی Layers", "New Layer, Duplicate, Mask, Opacity, Blending Modes", GuideSection.LAYERS),
            Triple("٤. دەستکاریکردنی ڕەنگ", "Curves, Levels, Vibrance, Hue/Sat, Color Balance", GuideSection.ADJUSTMENTS),
            Triple("٥. فلتەر و کاریگەرییەکان", "Blur Gallery, Sharpen, Liquify, Neural Filters", GuideSection.FILTERS),
            Triple("٦. نووسین و دیزاین", "Character, Paragraph, Shapes, Paths, Gradients", GuideSection.DESIGN),
            Triple("٧. فۆرماتەکانی پاشەکەوتکردن", "PSD, PSB, JPG, PNG, TIFF, PDF, GIF", GuideSection.FORMATS),
            Triple("داگرتنی ئەپی Kalar Edit (APK)", "داگرتنی ڕاستەوخۆ بۆ ناو مۆبایل و دامەزراندنی ئاسان", GuideSection.DOWNLOAD)
        )

        quickLinks.forEach { (title, desc, section) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToSection(section) },
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = KalarGold)
                        Text(text = desc, fontSize = 12.sp, color = TextSecondary)
                    }
                    Surface(
                        shape = CircleShape,
                        color = PhotoshopCyan.copy(alpha = 0.15f),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = PhotoshopCyan, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PanelCard(panel: PanelItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = panel.kurdishName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = DarkSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
                ) {
                    Text(
                        text = panel.name,
                        fontSize = 11.sp,
                        color = PhotoshopCyan,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = panel.description, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "کورتەپیت: ", fontSize = 11.sp, color = TextMuted)
                Text(text = panel.shortcut, fontSize = 11.sp, color = KalarGold, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun ToolCard(
    tool: ToolItem,
    onClick: () -> Unit,
    onTryInEditor: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("tool_card_${tool.id}"),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PhotoshopCyan.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PhotoshopCyan)
                    ) {
                        Text(
                            text = "[${tool.shortcut}]",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PhotoshopCyan,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = tool.kurdishName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(text = tool.name, fontSize = 11.sp, color = TextSecondary)
                    }
                }

                Button(
                    onClick = onTryInEditor,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceElevated),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PhotoshopCyan.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("تاقیبکەرەوە", fontSize = 11.sp, color = PhotoshopCyan)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = tool.description, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)
        }
    }
}

@Composable
fun LayerConceptCard(concept: LayerConcept, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = concept.kurdishName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = KalarGold)
                Text(text = concept.name, fontSize = 11.sp, color = TextMuted)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = concept.description, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "سوودی کرداری: ${concept.practicalUse}", fontSize = 11.sp, color = PhotoshopCyan)
        }
    }
}

@Composable
fun AdjustmentCard(concept: AdjustmentConcept, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = concept.kurdishName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = concept.name, fontSize = 11.sp, color = PhotoshopCyan)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = concept.description, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "ئامۆژگاری: ${concept.advice}", fontSize = 11.sp, color = KalarGold)
        }
    }
}

@Composable
fun FilterCard(concept: FilterConcept, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = concept.kurdishName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = concept.name, fontSize = 11.sp, color = PhotoshopCyan)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = concept.description, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "نموونەی بەکارهێنان: ${concept.usageExample}", fontSize = 11.sp, color = KalarGold)
        }
    }
}

@Composable
fun DesignCard(concept: DesignConcept, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = concept.kurdishName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(text = concept.name, fontSize = 11.sp, color = PhotoshopCyan)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = concept.description, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "ڕێنمایی پیشەیی: ${concept.bestPractices}", fontSize = 11.sp, color = KalarGold)
        }
    }
}

@Composable
fun FormatCard(format: FormatItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = KalarGold.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, KalarGold)
                ) {
                    Text(
                        text = format.format,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = KalarGold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Text(text = format.qualityLevel, fontSize = 11.sp, color = PhotoshopCyan)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = format.kurdishUsage, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = format.idealUse, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (format.layersSupport) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = null,
                        tint = if (format.layersSupport) Color(0xFF10B981) else Color(0xFFEF4444),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("چینەکان (Layers)", fontSize = 11.sp, color = TextSecondary)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (format.transparencySupport) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = null,
                        tint = if (format.transparencySupport) Color(0xFF10B981) else Color(0xFFEF4444),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("شەفافییەت (Alpha)", fontSize = 11.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
fun SearchResultsView(
    query: String,
    onItemClick: (Any) -> Unit,
    onOpenEditor: (String) -> Unit
) {
    val q = query.trim().lowercase()

    val matchedTools = PhotoshopGuideData.tools.filter {
        it.name.lowercase().contains(q) || it.kurdishName.contains(q) || it.shortcut.lowercase() == q
    }
    val matchedPanels = PhotoshopGuideData.panels.filter {
        it.name.lowercase().contains(q) || it.kurdishName.contains(q)
    }
    val matchedLayers = PhotoshopGuideData.layers.filter {
        it.name.lowercase().contains(q) || it.kurdishName.contains(q)
    }
    val matchedAdjustments = PhotoshopGuideData.adjustments.filter {
        it.name.lowercase().contains(q) || it.kurdishName.contains(q)
    }
    val matchedFilters = PhotoshopGuideData.filters.filter {
        it.name.lowercase().contains(q) || it.kurdishName.contains(q)
    }
    val matchedFormats = PhotoshopGuideData.formats.filter {
        it.format.lowercase().contains(q) || it.kurdishUsage.contains(q)
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "ئەنجامی گەڕان بۆ \"$query\":",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = PhotoshopCyan
        )

        if (matchedTools.isEmpty() && matchedPanels.isEmpty() && matchedLayers.isEmpty() && matchedAdjustments.isEmpty() && matchedFilters.isEmpty() && matchedFormats.isEmpty()) {
            Text(
                text = "هیچ ئەنجامێک نەدۆزرایەوە. وشەیەکی تر بنووسە وەک 'Brush', 'Pen', 'چین', 'PNG', 'Crop'.",
                fontSize = 13.sp,
                color = TextSecondary,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        matchedTools.forEach { tool ->
            ToolCard(tool = tool, onClick = { onItemClick(tool) }, onTryInEditor = { onOpenEditor(tool.id) })
        }
        matchedPanels.forEach { panel ->
            PanelCard(panel = panel, onClick = { onItemClick(panel) })
        }
        matchedLayers.forEach { layer ->
            LayerConceptCard(concept = layer, onClick = { onItemClick(layer) })
        }
        matchedAdjustments.forEach { adj ->
            AdjustmentCard(concept = adj, onClick = { onItemClick(adj) })
        }
        matchedFilters.forEach { f ->
            FilterCard(concept = f, onClick = { onItemClick(f) })
        }
        matchedFormats.forEach { fmt ->
            FormatCard(format = fmt, onClick = { onItemClick(fmt) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    item: Any,
    onDismiss: () -> Unit,
    onOpenEditor: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            when (item) {
                is ToolItem -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = item.kurdishName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = "${item.name} • Shortcut: [${item.shortcut}]", fontSize = 13.sp, color = PhotoshopCyan)
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "داخستن", tint = TextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = item.description, fontSize = 14.sp, color = TextPrimary, lineHeight = 20.sp)

                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "تایبەتمەندی و کارکردن:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = KalarGold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = item.details, fontSize = 12.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "ئامۆژگاری پسپۆڕان:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PhotoshopCyan)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = item.tips, fontSize = 12.sp, color = TextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                    Button(
                        onClick = { onOpenEditor(item.id) },
                        colors = ButtonDefaults.buttonColors(containerColor = PhotoshopCyan),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = DarkBg)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("تاقیکردنەوەی ئەم ئامرازە لە ستۆدیۆدا", color = DarkBg, fontWeight = FontWeight.Bold)
                    }
                }

                is FormatItem -> {
                    Text(text = "فۆرماتی ${item.format}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = KalarGold)
                    Text(text = item.kurdishUsage, fontSize = 13.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = item.idealUse, fontSize = 13.sp, color = TextPrimary, lineHeight = 20.sp)
                }

                is PanelItem -> {
                    Text(text = item.kurdishName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(text = item.name, fontSize = 13.sp, color = PhotoshopCyan)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = item.description, fontSize = 13.sp, color = TextPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "مەبەست لە بەکارهێنان: ${item.purpose}", fontSize = 12.sp, color = TextSecondary)
                }

                else -> {
                    Text(text = "زانیاری ورد", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun QuizInteractiveView() {
    val questions = PhotoshopGuideData.quizQuestions
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var isSubmitted by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val q = questions[currentIndex]

            Text(
                text = "پرسیاری ${currentIndex + 1} لە ${questions.size}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = KalarGold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = q.question,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            q.options.forEachIndexed { idx, opt ->
                val isSelected = selectedOptionIndex == idx
                val isCorrect = idx == q.correctIndex
                val btnColor = when {
                    isSubmitted && isCorrect -> Color(0xFF10B981)
                    isSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444)
                    isSelected -> PhotoshopCyan
                    else -> DarkSurface
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = btnColor.copy(alpha = if (isSelected || (isSubmitted && isCorrect)) 0.25f else 0.8f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected || (isSubmitted && isCorrect)) btnColor else DarkSurfaceBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(enabled = !isSubmitted) {
                            selectedOptionIndex = idx
                        }
                ) {
                    Text(
                        text = opt,
                        fontSize = 13.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!isSubmitted) {
                Button(
                    onClick = {
                        if (selectedOptionIndex != null) {
                            isSubmitted = true
                            if (selectedOptionIndex == q.correctIndex) {
                                score++
                            }
                        }
                    },
                    enabled = selectedOptionIndex != null,
                    colors = ButtonDefaults.buttonColors(containerColor = PhotoshopCyan),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("پشکنینی وەڵام", color = DarkBg, fontWeight = FontWeight.Bold)
                }
            } else {
                Text(
                    text = q.explanation,
                    fontSize = 12.sp,
                    color = KalarGold,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Button(
                    onClick = {
                        if (currentIndex < questions.lastIndex) {
                            currentIndex++
                            selectedOptionIndex = null
                            isSubmitted = false
                        } else {
                            // Reset
                            currentIndex = 0
                            selectedOptionIndex = null
                            score = 0
                            isSubmitted = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KalarGold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (currentIndex < questions.lastIndex) "پرسیاری داهاتوو" else "دووبارەکردنەوە (خاڵت: $score لە ${questions.size})",
                        color = DarkBg,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizModalSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "تاقیکردنەوەی زانیاری فۆتۆشۆپ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "داخستن", tint = TextSecondary)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            QuizInteractiveView()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
