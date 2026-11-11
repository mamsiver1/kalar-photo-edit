package com.example.ui.editor

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.BlendingMode
import com.example.model.CanvasLayer
import com.example.model.DrawStroke
import com.example.model.EditorToolType
import com.example.model.ImageAdjustments
import com.example.model.LayerType
import com.example.model.ShapeElement
import com.example.model.ShapeType
import com.example.model.TextElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.OutputStream

data class EditorUiState(
    val selectedTool: EditorToolType = EditorToolType.MOVE,
    val imageUri: Uri? = null,
    val useDefaultCastleImage: Boolean = true,
    val brushColor: Color = Color(0xFF31A8FF),
    val brushSize: Float = 14f,
    val strokes: List<DrawStroke> = emptyList(),
    val textElements: List<TextElement> = emptyList(),
    val shapeElements: List<ShapeElement> = emptyList(),
    val adjustments: ImageAdjustments = ImageAdjustments(),
    val layers: List<CanvasLayer> = listOf(
        CanvasLayer("bg", "پاشبنەمای وێنە (Background)", LayerType.BACKGROUND_IMAGE),
        CanvasLayer("draw", "چینی فڵچە و وێنەکێشان (Paint)", LayerType.PAINT),
        CanvasLayer("text", "چینی نووسین و دەق (Typography)", LayerType.TEXT),
        CanvasLayer("shapes", "چینی شێوەکان (Shapes)", LayerType.SHAPE)
    ),
    val activeLayerId: String = "draw",
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
    val exportSuccessMessage: String? = null,
    val isExporting: Boolean = false,
    val showLayersSheet: Boolean = false,
    val showAdjustmentsSheet: Boolean = false,
    val showExportDialog: Boolean = false,
    val showAddTextDialog: Boolean = false
)

class EditorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EditorUiState())
    val uiState: StateFlow<EditorUiState> = _uiState.asStateFlow()

    // Undo / Redo history stacks
    private val undoStack = mutableListOf<HistorySnapshot>()
    private val redoStack = mutableListOf<HistorySnapshot>()

    private data class HistorySnapshot(
        val strokes: List<DrawStroke>,
        val texts: List<TextElement>,
        val shapes: List<ShapeElement>,
        val adjustments: ImageAdjustments
    )

    private fun saveSnapshot() {
        val current = HistorySnapshot(
            strokes = _uiState.value.strokes,
            texts = _uiState.value.textElements,
            shapes = _uiState.value.shapeElements,
            adjustments = _uiState.value.adjustments
        )
        undoStack.add(current)
        redoStack.clear()
        _uiState.update { it.copy(canUndo = undoStack.isNotEmpty(), canRedo = false) }
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val last = undoStack.removeAt(undoStack.lastIndex)
            val current = HistorySnapshot(
                strokes = _uiState.value.strokes,
                texts = _uiState.value.textElements,
                shapes = _uiState.value.shapeElements,
                adjustments = _uiState.value.adjustments
            )
            redoStack.add(current)
            _uiState.update {
                it.copy(
                    strokes = last.strokes,
                    textElements = last.texts,
                    shapeElements = last.shapes,
                    adjustments = last.adjustments,
                    canUndo = undoStack.isNotEmpty(),
                    canRedo = true
                )
            }
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val next = redoStack.removeAt(redoStack.lastIndex)
            val current = HistorySnapshot(
                strokes = _uiState.value.strokes,
                texts = _uiState.value.textElements,
                shapes = _uiState.value.shapeElements,
                adjustments = _uiState.value.adjustments
            )
            undoStack.add(current)
            _uiState.update {
                it.copy(
                    strokes = next.strokes,
                    textElements = next.texts,
                    shapeElements = next.shapes,
                    adjustments = next.adjustments,
                    canUndo = true,
                    canRedo = redoStack.isNotEmpty()
                )
            }
        }
    }

    fun selectTool(tool: EditorToolType) {
        _uiState.update { it.copy(selectedTool = tool) }
        when (tool) {
            EditorToolType.LAYERS -> _uiState.update { it.copy(showLayersSheet = true) }
            EditorToolType.ADJUSTMENTS, EditorToolType.FILTERS -> _uiState.update { it.copy(showAdjustmentsSheet = true) }
            else -> {}
        }
    }

    fun setImageUri(uri: Uri?) {
        _uiState.update {
            it.copy(imageUri = uri, useDefaultCastleImage = (uri == null))
        }
    }

    fun setBrushColor(color: Color) {
        _uiState.update { it.copy(brushColor = color) }
    }

    fun setBrushSize(size: Float) {
        _uiState.update { it.copy(brushSize = size) }
    }

    fun addStroke(stroke: DrawStroke) {
        saveSnapshot()
        _uiState.update { it.copy(strokes = it.strokes + stroke) }
    }

    fun addText(text: String, color: Color = Color.White, isBold: Boolean = true) {
        saveSnapshot()
        val newText = TextElement(
            text = text,
            color = color,
            isBold = isBold,
            position = Offset(250f, 350f + (_uiState.value.textElements.size * 40f))
        )
        _uiState.update {
            it.copy(
                textElements = it.textElements + newText,
                showAddTextDialog = false
            )
        }
    }

    fun updateTextPosition(index: Int, newOffset: Offset) {
        val currentTexts = _uiState.value.textElements.toMutableList()
        if (index in currentTexts.indices) {
            currentTexts[index] = currentTexts[index].copy(position = newOffset)
            _uiState.update { it.copy(textElements = currentTexts) }
        }
    }

    fun removeText(index: Int) {
        saveSnapshot()
        val currentTexts = _uiState.value.textElements.toMutableList()
        if (index in currentTexts.indices) {
            currentTexts.removeAt(index)
            _uiState.update { it.copy(textElements = currentTexts) }
        }
    }

    fun addShape(type: ShapeType) {
        saveSnapshot()
        val shape = ShapeElement(
            type = type,
            color = _uiState.value.brushColor,
            position = Offset(280f, 380f + (_uiState.value.shapeElements.size * 30f))
        )
        _uiState.update { it.copy(shapeElements = it.shapeElements + shape) }
    }

    fun updateShapePosition(index: Int, newOffset: Offset) {
        val currentShapes = _uiState.value.shapeElements.toMutableList()
        if (index in currentShapes.indices) {
            currentShapes[index] = currentShapes[index].copy(position = newOffset)
            _uiState.update { it.copy(shapeElements = currentShapes) }
        }
    }

    fun updateAdjustments(newAdjustments: ImageAdjustments) {
        saveSnapshot()
        _uiState.update { it.copy(adjustments = newAdjustments) }
    }

    fun resetAdjustments() {
        saveSnapshot()
        _uiState.update { it.copy(adjustments = ImageAdjustments()) }
    }

    fun clearCanvasDrawings() {
        saveSnapshot()
        _uiState.update { it.copy(strokes = emptyList()) }
    }

    fun toggleLayerVisibility(layerId: String) {
        val updated = _uiState.value.layers.map {
            if (it.id == layerId) it.copy(isVisible = !it.isVisible) else it
        }
        _uiState.update { it.copy(layers = updated) }
    }

    fun setLayerOpacity(layerId: String, opacity: Float) {
        val updated = _uiState.value.layers.map {
            if (it.id == layerId) it.copy(opacity = opacity.coerceIn(0f, 1f)) else it
        }
        _uiState.update { it.copy(layers = updated) }
    }

    fun setLayerBlendMode(layerId: String, mode: BlendingMode) {
        val updated = _uiState.value.layers.map {
            if (it.id == layerId) it.copy(blendMode = mode) else it
        }
        _uiState.update { it.copy(layers = updated) }
    }

    fun setLayersSheetVisible(visible: Boolean) {
        _uiState.update { it.copy(showLayersSheet = visible) }
    }

    fun setAdjustmentsSheetVisible(visible: Boolean) {
        _uiState.update { it.copy(showAdjustmentsSheet = visible) }
    }

    fun setExportDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(showExportDialog = visible) }
    }

    fun setAddTextDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(showAddTextDialog = visible) }
    }

    fun clearExportMessage() {
        _uiState.update { it.copy(exportSuccessMessage = null) }
    }

    fun exportImage(context: Context, format: String, qualityPercent: Int = 95) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isExporting = true) }
            try {
                // Generate merged Bitmap
                val width = 1080
                val height = 1080
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)

                // 1. Draw Background
                val bgPaint = Paint().apply { isAntiAlias = true }
                val baseBitmap = if (_uiState.value.imageUri != null) {
                    val stream = context.contentResolver.openInputStream(_uiState.value.imageUri!!)
                    BitmapFactory.decodeStream(stream)
                } else {
                    BitmapFactory.decodeResource(context.resources, com.example.R.drawable.kalar_logo)
                }

                if (baseBitmap != null) {
                    // Apply image adjustments (Brightness, Contrast, Saturation, B&W, Invert)
                    val cm = ColorMatrix()
                    val adj = _uiState.value.adjustments

                    // Brightness & Contrast
                    val c = adj.contrast
                    val b = adj.brightness * 2.55f // -255 to 255
                    val scale = c
                    val translate = (-0.5f * scale + 0.5f) * 255f + b
                    val cmContrast = ColorMatrix(floatArrayOf(
                        scale, 0f, 0f, 0f, translate,
                        0f, scale, 0f, 0f, translate,
                        0f, 0f, scale, 0f, translate,
                        0f, 0f, 0f, 1f, 0f
                    ))
                    cm.postConcat(cmContrast)

                    // Saturation
                    if (adj.isBlackAndWhite) {
                        val cmBW = ColorMatrix().apply { setSaturation(0f) }
                        cm.postConcat(cmBW)
                    } else if (adj.saturation != 1f) {
                        val cmSat = ColorMatrix().apply { setSaturation(adj.saturation) }
                        cm.postConcat(cmSat)
                    }

                    // Invert
                    if (adj.isInvert) {
                        val cmInvert = ColorMatrix(floatArrayOf(
                            -1f, 0f, 0f, 0f, 255f,
                            0f, -1f, 0f, 0f, 255f,
                            0f, 0f, -1f, 0f, 255f,
                            0f, 0f, 0f, 1f, 0f
                        ))
                        cm.postConcat(cmInvert)
                    }

                    bgPaint.colorFilter = ColorMatrixColorFilter(cm)

                    val bgLayer = _uiState.value.layers.find { it.type == LayerType.BACKGROUND_IMAGE }
                    if (bgLayer?.isVisible != false) {
                        bgPaint.alpha = ((bgLayer?.opacity ?: 1f) * 255).toInt()
                        val srcRect = android.graphics.Rect(0, 0, baseBitmap.width, baseBitmap.height)
                        val dstRect = android.graphics.Rect(0, 0, width, height)
                        canvas.drawBitmap(baseBitmap, srcRect, dstRect, bgPaint)
                    }
                }

                // 2. Draw Paint Strokes
                val paintLayer = _uiState.value.layers.find { it.type == LayerType.PAINT }
                if (paintLayer?.isVisible != false) {
                    val strokePaint = Paint().apply {
                        isAntiAlias = true
                        style = Paint.Style.STROKE
                        strokeCap = Paint.Cap.ROUND
                        strokeJoin = Paint.Join.ROUND
                        alpha = ((paintLayer?.opacity ?: 1f) * 255).toInt()
                    }
                    for (stroke in _uiState.value.strokes) {
                        strokePaint.color = stroke.color.toArgb()
                        strokePaint.strokeWidth = stroke.strokeWidth * (width / 500f) // scale to export res
                        if (stroke.points.size > 1) {
                            val path = android.graphics.Path()
                            val first = stroke.points.first()
                            path.moveTo(first.x * (width / 500f), first.y * (height / 500f))
                            for (p in stroke.points.drop(1)) {
                                path.lineTo(p.x * (width / 500f), p.y * (height / 500f))
                            }
                            canvas.drawPath(path, strokePaint)
                        }
                    }
                }

                // 3. Draw Shapes
                val shapeLayer = _uiState.value.layers.find { it.type == LayerType.SHAPE }
                if (shapeLayer?.isVisible != false) {
                    val sPaint = Paint().apply {
                        isAntiAlias = true
                        style = Paint.Style.STROKE
                        strokeWidth = 8f
                        alpha = ((shapeLayer?.opacity ?: 1f) * 255).toInt()
                    }
                    for (sh in _uiState.value.shapeElements) {
                        sPaint.color = sh.color.toArgb()
                        val sx = sh.position.x * (width / 500f)
                        val sy = sh.position.y * (height / 500f)
                        val sSize = sh.size * (width / 500f)
                        when (sh.type) {
                            ShapeType.CIRCLE -> canvas.drawCircle(sx, sy, sSize / 2, sPaint)
                            ShapeType.RECTANGLE -> canvas.drawRect(sx - sSize / 2, sy - sSize / 2, sx + sSize / 2, sy + sSize / 2, sPaint)
                            ShapeType.FRAME -> {
                                sPaint.strokeWidth = 14f
                                canvas.drawRect(sx - sSize / 2, sy - sSize / 2, sx + sSize / 2, sy + sSize / 2, sPaint)
                            }
                            ShapeType.STAR -> canvas.drawCircle(sx, sy, sSize / 2, sPaint)
                        }
                    }
                }

                // 4. Draw Texts
                val textLayer = _uiState.value.layers.find { it.type == LayerType.TEXT }
                if (textLayer?.isVisible != false) {
                    val tPaint = Paint().apply {
                        isAntiAlias = true
                        textSize = 48f
                        isFakeBoldText = true
                        alpha = ((textLayer?.opacity ?: 1f) * 255).toInt()
                    }
                    for (te in _uiState.value.textElements) {
                        tPaint.color = te.color.toArgb()
                        val tx = te.position.x * (width / 500f)
                        val ty = te.position.y * (height / 500f)
                        canvas.drawText(te.text, tx, ty, tPaint)
                    }
                }

                // Save to MediaStore (Gallery / Pictures)
                val ext = if (format.contains("PNG", ignoreCase = true)) "png" else "jpg"
                val mimeType = if (ext == "png") "image/png" else "image/jpeg"
                val compressFormat = if (ext == "png") Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG

                val filename = "KalarEdit_${System.currentTimeMillis()}.$ext"
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/KalarEdit")
                        put(MediaStore.Images.Media.IS_PENDING, 1)
                    }
                }

                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                if (uri != null) {
                    val out: OutputStream? = context.contentResolver.openOutputStream(uri)
                    if (out != null) {
                        bitmap.compress(compressFormat, qualityPercent, out)
                        out.flush()
                        out.close()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
                        context.contentResolver.update(uri, values, null, null)
                    }
                }

                _uiState.update {
                    it.copy(
                        isExporting = false,
                        showExportDialog = false,
                        exportSuccessMessage = "وێنەکە بە سەرکەوتوویی پاشەکەوتکرا بە فۆرماتی $format لە بەشی وێنەکانت (Pictures/KalarEdit)"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportSuccessMessage = "هەڵە لە پاشەکەوتکردن: ${e.localizedMessage ?: "نەتوانرا پاشەکەوت بکرێت"}"
                    )
                }
            }
        }
    }
}
