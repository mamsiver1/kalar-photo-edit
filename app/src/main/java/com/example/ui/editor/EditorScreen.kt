package com.example.ui.editor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.toArgb
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FormatColorReset
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.model.BlendingMode
import com.example.model.DrawStroke
import com.example.model.EditorToolType
import com.example.model.LayerType
import com.example.model.ShapeType
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.KalarGold
import com.example.ui.theme.PhotoshopCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    viewModel: EditorViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Photo picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setImageUri(uri)
        }
    }

    LaunchedEffect(uiState.exportSuccessMessage) {
        uiState.exportSuccessMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearExportMessage()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBg,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ستۆدیۆی کەلار ئیدت",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = PhotoshopCyan.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PhotoshopCyan)
                        ) {
                            Text(
                                text = "${uiState.selectedTool.labelKurdish} [${uiState.selectedTool.shortcut}]",
                                fontSize = 11.sp,
                                color = PhotoshopCyan,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("editor_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "گەڕانەوە بۆ ڕێبەر",
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.undo() },
                        enabled = uiState.canUndo,
                        modifier = Modifier.testTag("undo_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Undo,
                            contentDescription = "گەڕانەوە بەرەودوا",
                            tint = if (uiState.canUndo) TextPrimary else TextMuted
                        )
                    }
                    IconButton(
                        onClick = { viewModel.redo() },
                        enabled = uiState.canRedo,
                        modifier = Modifier.testTag("redo_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Redo,
                            contentDescription = "چوونە پێشەوە",
                            tint = if (uiState.canRedo) TextPrimary else TextMuted
                        )
                    }
                    IconButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier.testTag("pick_image_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Photo,
                            contentDescription = "کردنەوەی وێنە لە گەلەری",
                            tint = PhotoshopCyan
                        )
                    }
                    IconButton(
                        onClick = { viewModel.setExportDialogVisible(true) },
                        modifier = Modifier.testTag("export_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "پاشەکەوتکردنی وێنە",
                            tint = KalarGold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSurface)
            ) {
                // Tool contextual options bar
                ToolContextualBar(
                    uiState = uiState,
                    viewModel = viewModel
                )

                // Main Photoshop Toolbar row
                PhotoshopToolbarRow(
                    selectedTool = uiState.selectedTool,
                    onToolSelected = { viewModel.selectTool(it) }
                )

                // Footer Credit
                Text(
                    text = "دروستکراوە لەلایەن SIVER IT",
                    fontSize = 11.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBg),
            contentAlignment = Alignment.Center
        ) {
            // Interactive Photo Editor Canvas
            InteractiveEditorCanvas(
                uiState = uiState,
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, DarkSurfaceBorder, RoundedCornerShape(8.dp))
                    .shadow(12.dp)
            )
        }
    }

    // Modal: Layers Panel
    if (uiState.showLayersSheet) {
        LayersBottomSheet(
            uiState = uiState,
            viewModel = viewModel,
            onDismiss = { viewModel.setLayersSheetVisible(false) }
        )
    }

    // Modal: Color & Adjustments Sheet
    if (uiState.showAdjustmentsSheet) {
        AdjustmentsBottomSheet(
            uiState = uiState,
            viewModel = viewModel,
            onDismiss = { viewModel.setAdjustmentsSheetVisible(false) }
        )
    }

    // Dialog: Add Text
    if (uiState.showAddTextDialog) {
        AddTextDialog(
            brushColor = uiState.brushColor,
            onDismiss = { viewModel.setAddTextDialogVisible(false) },
            onConfirm = { text, color ->
                viewModel.addText(text, color)
            }
        )
    }

    // Dialog: Export / Save
    if (uiState.showExportDialog) {
        ExportImageDialog(
            isExporting = uiState.isExporting,
            onDismiss = { viewModel.setExportDialogVisible(false) },
            onExport = { format, quality ->
                viewModel.exportImage(context, format, quality)
            }
        )
    }
}

@Composable
fun InteractiveEditorCanvas(
    uiState: EditorUiState,
    viewModel: EditorViewModel,
    modifier: Modifier = Modifier
) {
    var currentStrokePoints by remember { mutableStateOf<List<Offset>>(emptyList()) }

    BoxWithConstraints(
        modifier = modifier.background(Color(0xFF1E2024)),
        contentAlignment = Alignment.Center
    ) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()

        // 1. Background image layer
        val bgLayer = uiState.layers.find { it.type == LayerType.BACKGROUND_IMAGE }
        if (bgLayer?.isVisible != false) {
            // Compute ColorMatrix for brightness, contrast, saturation, b&w, invert
            val colorMatrix = remember(uiState.adjustments) {
                val cm = ColorMatrix()
                val adj = uiState.adjustments

                val c = adj.contrast
                val b = adj.brightness * 2.55f
                val scale = c
                val translate = (-0.5f * scale + 0.5f) * 255f + b
                val cmContrast = ColorMatrix(floatArrayOf(
                    scale, 0f, 0f, 0f, translate,
                    0f, scale, 0f, 0f, translate,
                    0f, 0f, scale, 0f, translate,
                    0f, 0f, 0f, 1f, 0f
                ))
                cm.timesAssign(cmContrast)

                if (adj.isBlackAndWhite) {
                    val cmBW = ColorMatrix().apply { setToSaturation(0f) }
                    cm.timesAssign(cmBW)
                } else if (adj.saturation != 1f) {
                    val cmSat = ColorMatrix().apply { setToSaturation(adj.saturation) }
                    cm.timesAssign(cmSat)
                }

                if (adj.isInvert) {
                    val cmInvert = ColorMatrix(floatArrayOf(
                        -1f, 0f, 0f, 0f, 255f,
                        0f, -1f, 0f, 0f, 255f,
                        0f, 0f, -1f, 0f, 255f,
                        0f, 0f, 0f, 1f, 0f
                    ))
                    cm.timesAssign(cmInvert)
                }
                cm
            }

            val imageModel = uiState.imageUri ?: R.drawable.kalar_logo
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageModel)
                    .crossfade(true)
                    .build(),
                contentDescription = "کەنڤاسی دەستکاری",
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(colorMatrix),
                alpha = bgLayer?.opacity ?: 1f
            )
        }

        // 2. Multi-layer Drawing & Interactive Canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(uiState.selectedTool, uiState.brushColor, uiState.brushSize) {
                    when (uiState.selectedTool) {
                        EditorToolType.BRUSH -> {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentStrokePoints = listOf(offset)
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    currentStrokePoints = currentStrokePoints + change.position
                                },
                                onDragEnd = {
                                    if (currentStrokePoints.isNotEmpty()) {
                                        viewModel.addStroke(
                                            DrawStroke(
                                                points = currentStrokePoints,
                                                color = uiState.brushColor,
                                                strokeWidth = uiState.brushSize,
                                                isEraser = false
                                            )
                                        )
                                        currentStrokePoints = emptyList()
                                    }
                                }
                            )
                        }
                        EditorToolType.ERASER -> {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentStrokePoints = listOf(offset)
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    currentStrokePoints = currentStrokePoints + change.position
                                },
                                onDragEnd = {
                                    if (currentStrokePoints.isNotEmpty()) {
                                        viewModel.addStroke(
                                            DrawStroke(
                                                points = currentStrokePoints,
                                                color = Color(0xFF1E2024),
                                                strokeWidth = uiState.brushSize * 1.5f,
                                                isEraser = true
                                            )
                                        )
                                        currentStrokePoints = emptyList()
                                    }
                                }
                            )
                        }
                        EditorToolType.MOVE -> {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                // Move the nearest text element if clicked
                                if (uiState.textElements.isNotEmpty()) {
                                    val lastIdx = uiState.textElements.lastIndex
                                    val currentPos = uiState.textElements[lastIdx].position
                                    viewModel.updateTextPosition(lastIdx, currentPos + dragAmount)
                                } else if (uiState.shapeElements.isNotEmpty()) {
                                    val lastIdx = uiState.shapeElements.lastIndex
                                    val currentPos = uiState.shapeElements[lastIdx].position
                                    viewModel.updateShapePosition(lastIdx, currentPos + dragAmount)
                                }
                            }
                        }
                        else -> {
                            detectTapGestures { }
                        }
                    }
                }
        ) {
            // Paint Layer
            val paintLayer = uiState.layers.find { it.type == LayerType.PAINT }
            if (paintLayer?.isVisible != false) {
                val layerAlpha = paintLayer?.opacity ?: 1f
                // Existing strokes
                for (stroke in uiState.strokes) {
                    if (stroke.points.size > 1) {
                        val path = Path().apply {
                            moveTo(stroke.points.first().x, stroke.points.first().y)
                            for (p in stroke.points.drop(1)) {
                                lineTo(p.x, p.y)
                            }
                        }
                        drawPath(
                            path = path,
                            color = stroke.color.copy(alpha = stroke.color.alpha * layerAlpha),
                            style = Stroke(
                                width = stroke.strokeWidth,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                // Currently drawing stroke
                if (currentStrokePoints.size > 1) {
                    val path = Path().apply {
                        moveTo(currentStrokePoints.first().x, currentStrokePoints.first().y)
                        for (p in currentStrokePoints.drop(1)) {
                            lineTo(p.x, p.y)
                        }
                    }
                    val strokeColor = if (uiState.selectedTool == EditorToolType.ERASER) {
                        Color(0xFF1E2024)
                    } else {
                        uiState.brushColor
                    }
                    drawPath(
                        path = path,
                        color = strokeColor.copy(alpha = strokeColor.alpha * layerAlpha),
                        style = Stroke(
                            width = if (uiState.selectedTool == EditorToolType.ERASER) uiState.brushSize * 1.5f else uiState.brushSize,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // Shape Layer
            val shapeLayer = uiState.layers.find { it.type == LayerType.SHAPE }
            if (shapeLayer?.isVisible != false) {
                val shapeAlpha = shapeLayer?.opacity ?: 1f
                for (sh in uiState.shapeElements) {
                    val col = sh.color.copy(alpha = sh.color.alpha * shapeAlpha)
                    when (sh.type) {
                        ShapeType.CIRCLE -> {
                            drawCircle(
                                color = col,
                                radius = sh.size / 2,
                                center = sh.position,
                                style = Stroke(width = sh.strokeWidth)
                            )
                        }
                        ShapeType.RECTANGLE -> {
                            drawRect(
                                color = col,
                                topLeft = Offset(sh.position.x - sh.size / 2, sh.position.y - sh.size / 2),
                                size = Size(sh.size, sh.size),
                                style = Stroke(width = sh.strokeWidth)
                            )
                        }
                        ShapeType.FRAME -> {
                            drawRect(
                                color = col,
                                topLeft = Offset(sh.position.x - sh.size * 0.7f, sh.position.y - sh.size * 0.7f),
                                size = Size(sh.size * 1.4f, sh.size * 1.4f),
                                style = Stroke(width = sh.strokeWidth * 2f)
                            )
                        }
                        ShapeType.STAR -> {
                            drawCircle(
                                color = col,
                                radius = sh.size / 2,
                                center = sh.position,
                                style = Stroke(width = sh.strokeWidth)
                            )
                        }
                    }
                }
            }

            // Text Layer
            val textLayer = uiState.layers.find { it.type == LayerType.TEXT }
            if (textLayer?.isVisible != false) {
                val textAlpha = textLayer?.opacity ?: 1f
                drawContext.canvas.nativeCanvas.apply {
                    val paint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        textSize = 42f
                        isFakeBoldText = true
                    }
                    for (te in uiState.textElements) {
                        paint.color = te.color.copy(alpha = te.color.alpha * textAlpha).toArgb()
                        drawText(te.text, te.position.x, te.position.y, paint)
                    }
                }
            }
        }
    }
}

@Composable
fun ToolContextualBar(
    uiState: EditorUiState,
    viewModel: EditorViewModel
) {
    val presetColors = listOf(
        PhotoshopCyan,
        KalarGold,
        Color.White,
        Color(0xFFEF4444), // Red
        Color(0xFF10B981), // Green
        Color(0xFF8B5CF6), // Purple
        Color(0xFFEC4899), // Pink
        Color(0xFFF97316), // Orange
        Color(0xFF06B6D4)  // Cyan
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DarkSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            when (uiState.selectedTool) {
                EditorToolType.BRUSH, EditorToolType.ERASER -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (uiState.selectedTool == EditorToolType.BRUSH) "قەبارەی فڵچە:" else "قەبارەی سڕەر:",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Slider(
                            value = uiState.brushSize,
                            onValueChange = { viewModel.setBrushSize(it) },
                            valueRange = 4f..50f,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = PhotoshopCyan,
                                activeTrackColor = PhotoshopCyan
                            )
                        )
                        Text(
                            text = "${uiState.brushSize.toInt()} px",
                            fontSize = 12.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { viewModel.clearCanvasDrawings() },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatColorReset,
                                contentDescription = "پاککردنەوەی کێشراوەکان",
                                tint = Color(0xFFEF4444)
                            )
                        }
                    }

                    if (uiState.selectedTool == EditorToolType.BRUSH) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            items(presetColors) { color ->
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .border(
                                            width = if (uiState.brushColor == color) 2.dp else 1.dp,
                                            color = if (uiState.brushColor == color) Color.White else DarkSurfaceBorder,
                                            shape = CircleShape
                                        )
                                        .clickable { viewModel.setBrushColor(color) }
                                )
                            }
                        }
                    }
                }

                EditorToolType.TEXT -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ژمارەی دەقەکان: ${uiState.textElements.size}",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Button(
                            onClick = { viewModel.setAddTextDialogVisible(true) },
                            colors = ButtonDefaults.buttonColors(containerColor = PhotoshopCyan),
                            modifier = Modifier.testTag("add_text_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("زیادکردنی دەق", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        if (uiState.textElements.isNotEmpty()) {
                            OutlinedButton(
                                onClick = { viewModel.removeText(uiState.textElements.lastIndex) }
                            ) {
                                Text("سڕینەوەی دەقی کۆتایی", fontSize = 11.sp, color = Color(0xFFEF4444))
                            }
                        }
                    }
                }

                EditorToolType.SHAPES -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "شێوە ئەندازیارییەکان:",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            ShapeType.values().forEach { shapeType ->
                                OutlinedButton(
                                    onClick = { viewModel.addShape(shapeType) },
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Text(shapeType.kurdishTitle, fontSize = 11.sp, color = TextPrimary)
                                }
                            }
                        }
                    }
                }

                EditorToolType.MOVE -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "بە دەست ڕابکێشە بۆ گواستنەوەی دەق و شێوەکان",
                            fontSize = 12.sp,
                            color = PhotoshopCyan
                        )
                        Text(
                            text = "Move Tool [V]",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }

                EditorToolType.ADJUSTMENTS, EditorToolType.FILTERS -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "دەستکاریکردنی ڕەنگ و ڕووناکی",
                            fontSize = 12.sp,
                            color = KalarGold
                        )
                        Button(
                            onClick = { viewModel.setAdjustmentsSheetVisible(true) },
                            colors = ButtonDefaults.buttonColors(containerColor = KalarGold)
                        ) {
                            Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("کردنەوەی پەنجەرەی ڕەنگ", fontSize = 12.sp, color = DarkBg)
                        }
                    }
                }

                EditorToolType.LAYERS -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "پەنجەرەی چینەکان (Layers Panel) [F7]",
                            fontSize = 12.sp,
                            color = PhotoshopCyan
                        )
                        Button(
                            onClick = { viewModel.setLayersSheetVisible(true) },
                            colors = ButtonDefaults.buttonColors(containerColor = PhotoshopCyan)
                        ) {
                            Icon(Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("بەڕێوەبردنی چینەکان", fontSize = 12.sp, color = DarkBg)
                        }
                    }
                }

                else -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${uiState.selectedTool.labelKurdish} هەڵبژێردراوە",
                            fontSize = 12.sp,
                            color = TextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoshopToolbarRow(
    selectedTool: EditorToolType,
    onToolSelected: (EditorToolType) -> Unit
) {
    val tools = listOf(
        EditorToolType.MOVE to Icons.Default.OpenInBrowser,
        EditorToolType.BRUSH to Icons.Default.Brush,
        EditorToolType.ERASER to Icons.Default.Delete,
        EditorToolType.TEXT to Icons.Default.TextFields,
        EditorToolType.SHAPES to Icons.Default.Category,
        EditorToolType.ADJUSTMENTS to Icons.Default.Tune,
        EditorToolType.FILTERS to Icons.Default.AutoFixHigh,
        EditorToolType.LAYERS to Icons.Default.Layers,
        EditorToolType.CROP to Icons.Default.Crop,
        EditorToolType.EYEDROPPER to Icons.Default.Colorize
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(tools) { (tool, icon) ->
            val isSelected = selectedTool == tool
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) PhotoshopCyan else DarkSurfaceElevated,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) PhotoshopCyan else DarkSurfaceBorder
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onToolSelected(tool) }
                    .testTag("tool_${tool.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = tool.labelKurdish,
                        tint = if (isSelected) DarkBg else TextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = tool.labelKurdish,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) DarkBg else TextPrimary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayersBottomSheet(
    uiState: EditorUiState,
    viewModel: EditorViewModel,
    onDismiss: () -> Unit
) {
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
                    text = "پەنجەرەی چینەکان (Layers Panel)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "داخستن", tint = TextSecondary)
                }
            }

            Text(
                text = "کۆنتڕۆڵی بینین، شەفافییەت (Opacity)، و تێکەڵکردنی چینەکان (Blending Mode)",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            uiState.layers.forEach { layer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { viewModel.toggleLayerVisibility(layer.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = if (layer.isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "بینین",
                                        tint = if (layer.isVisible) PhotoshopCyan else TextMuted
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = layer.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (layer.isVisible) TextPrimary else TextMuted
                                )
                            }

                            Text(
                                text = "${(layer.opacity * 100).toInt()}%",
                                fontSize = 12.sp,
                                color = KalarGold
                            )
                        }

                        // Opacity slider
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Opacity:", fontSize = 11.sp, color = TextSecondary)
                            Slider(
                                value = layer.opacity,
                                onValueChange = { viewModel.setLayerOpacity(layer.id, it) },
                                valueRange = 0f..1f,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = KalarGold,
                                    activeTrackColor = KalarGold
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdjustmentsBottomSheet(
    uiState: EditorUiState,
    viewModel: EditorViewModel,
    onDismiss: () -> Unit
) {
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
                    text = "دەستکاریکردنی ڕەنگ و وێنە (Adjustments)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "داخستن", tint = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Brightness
            Text("ڕووناکی (Brightness): ${uiState.adjustments.brightness.toInt()}%", fontSize = 13.sp, color = TextPrimary)
            Slider(
                value = uiState.adjustments.brightness,
                onValueChange = { viewModel.updateAdjustments(uiState.adjustments.copy(brightness = it)) },
                valueRange = -100f..100f,
                colors = SliderDefaults.colors(thumbColor = PhotoshopCyan, activeTrackColor = PhotoshopCyan)
            )

            // Contrast
            Text("کۆنتراست و جیاوازی (Contrast): ${String.format("%.1f", uiState.adjustments.contrast)}x", fontSize = 13.sp, color = TextPrimary)
            Slider(
                value = uiState.adjustments.contrast,
                onValueChange = { viewModel.updateAdjustments(uiState.adjustments.copy(contrast = it)) },
                valueRange = 0.5f..2.5f,
                colors = SliderDefaults.colors(thumbColor = PhotoshopCyan, activeTrackColor = PhotoshopCyan)
            )

            // Saturation
            Text("تێری ڕەنگ (Saturation / Vibrance): ${String.format("%.1f", uiState.adjustments.saturation)}x", fontSize = 13.sp, color = TextPrimary)
            Slider(
                value = uiState.adjustments.saturation,
                onValueChange = { viewModel.updateAdjustments(uiState.adjustments.copy(saturation = it)) },
                valueRange = 0f..2.5f,
                colors = SliderDefaults.colors(thumbColor = PhotoshopCyan, activeTrackColor = PhotoshopCyan)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Toggles for Black & White, Invert
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.updateAdjustments(
                            uiState.adjustments.copy(isBlackAndWhite = !uiState.adjustments.isBlackAndWhite)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.adjustments.isBlackAndWhite) PhotoshopCyan else DarkSurfaceElevated
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("ڕەش و سپی (B&W)", fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        viewModel.updateAdjustments(
                            uiState.adjustments.copy(isInvert = !uiState.adjustments.isInvert)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.adjustments.isInvert) PhotoshopCyan else DarkSurfaceElevated
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("پێچەوانەکردن (Invert)", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.resetAdjustments() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("گەڕاندنەوە بۆ دۆخی سەرەتا (Reset)")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AddTextDialog(
    brushColor: Color,
    onDismiss: () -> Unit,
    onConfirm: (String, Color) -> Unit
) {
    var textInput by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(brushColor) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("زیادکردنی دەق (Type Tool [T])", color = TextPrimary) },
        text = {
            Column {
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    label = { Text("دەقی دڵخوازت بنووسە...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("text_input_field")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (textInput.isNotBlank()) {
                        onConfirm(textInput, selectedColor)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PhotoshopCyan),
                modifier = Modifier.testTag("confirm_add_text_button")
            ) {
                Text("زیادکردن", color = DarkBg, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("پەشیمانبوونەوە", color = TextSecondary)
            }
        },
        containerColor = DarkSurface
    )
}

@Composable
fun ExportImageDialog(
    isExporting: Boolean,
    onDismiss: () -> Unit,
    onExport: (format: String, quality: Int) -> Unit
) {
    var selectedFormat by remember { mutableStateOf("PNG") }
    var quality by remember { mutableStateOf(95f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("پاشەکەوتکردنی وێنە (Export Formats)", fontWeight = FontWeight.Bold, color = TextPrimary)
        },
        text = {
            Column {
                Text(
                    text = "فۆرماتی پاشەکەوتکردن هەڵبژێرە:",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("PNG", "JPG / JPEG").forEach { fmt ->
                        val isSelected = selectedFormat == fmt
                        Button(
                            onClick = { selectedFormat = fmt },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) PhotoshopCyan else DarkSurfaceElevated
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(fmt, color = if (isSelected) DarkBg else TextPrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (selectedFormat == "PNG") {
                        "PNG: کوالێتی بەرز و پاراستنی شەفافییەت (Lossless)."
                    } else {
                        "JPG: گونجاوە بۆ تۆڕە کۆمەڵایەتییەکان بە قەبارەیەکی کەم."
                    },
                    fontSize = 12.sp,
                    color = KalarGold
                )

                if (selectedFormat.contains("JPG")) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("کوالێتی وێنە: ${quality.toInt()}%", fontSize = 12.sp, color = TextPrimary)
                    Slider(
                        value = quality,
                        onValueChange = { quality = it },
                        valueRange = 50f..100f,
                        colors = SliderDefaults.colors(thumbColor = PhotoshopCyan, activeTrackColor = PhotoshopCyan)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onExport(selectedFormat, quality.toInt()) },
                enabled = !isExporting,
                colors = ButtonDefaults.buttonColors(containerColor = KalarGold),
                modifier = Modifier.testTag("confirm_export_button")
            ) {
                Text(
                    text = if (isExporting) "پاشەکەوت دەکرێت..." else "پاشەکەوتکردن لە گەلەری",
                    color = DarkBg,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("داخستن", color = TextSecondary)
            }
        },
        containerColor = DarkSurface
    )
}
