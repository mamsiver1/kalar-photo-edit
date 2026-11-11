package com.example.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

enum class EditorToolType(val labelKurdish: String, val shortcut: String) {
    MOVE("گواستنەوە", "V"),
    BRUSH("فڵچە", "B"),
    ERASER("سڕینەوە", "E"),
    TEXT("دەق", "T"),
    SHAPES("شێوەکان", "U"),
    ADJUSTMENTS("ڕەنگ", "Ctrl+M"),
    FILTERS("فلتەر", "Ctrl+F"),
    EYEDROPPER("وەرگرتنی ڕەنگ", "I"),
    LAYERS("چینەکان", "F7"),
    CROP("بڕین", "C")
}

enum class LayerType {
    BACKGROUND_IMAGE,
    PAINT,
    TEXT,
    SHAPE
}

enum class BlendingMode(val title: String) {
    NORMAL("Normal - ئاسایی"),
    MULTIPLY("Multiply - تاریککردن"),
    SCREEN("Screen - ڕووناککردن"),
    OVERLAY("Overlay - لێکدانی کۆنتراست")
}

data class DrawPoint(
    val offset: Offset
)

data class DrawStroke(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float,
    val isEraser: Boolean = false
)

data class TextElement(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val position: Offset = Offset(200f, 300f),
    val color: Color = Color.White,
    val fontSizeSp: Float = 24f,
    val isBold: Boolean = false,
    val hasBackgroundPill: Boolean = false,
    val backgroundColor: Color = Color(0x99000000)
)

enum class ShapeType(val kurdishTitle: String) {
    RECTANGLE("چوارگۆشە"),
    CIRCLE("بازنە"),
    STAR("ئەستێرە"),
    FRAME("چوارچێوە")
}

data class ShapeElement(
    val id: Long = System.currentTimeMillis(),
    val type: ShapeType,
    val position: Offset = Offset(250f, 350f),
    val size: Float = 140f,
    val color: Color = Color(0xFF31A8FF),
    val strokeWidth: Float = 6f,
    val isFilled: Boolean = false
)

data class ImageAdjustments(
    val brightness: Float = 0f,       // -100 to +100 (%)
    val contrast: Float = 1f,         // 0.5x to 2.0x
    val saturation: Float = 1f,       // 0x to 2.5x
    val hueShift: Float = 0f,         // -180 to +180 deg
    val isBlackAndWhite: Boolean = false,
    val isSepia: Boolean = false,
    val isInvert: Boolean = false,
    val blurRadius: Float = 0f        // 0 to 20
)

data class CanvasLayer(
    val id: String,
    val title: String,
    val type: LayerType,
    val isVisible: Boolean = true,
    val opacity: Float = 1.0f,
    val blendMode: BlendingMode = BlendingMode.NORMAL,
    val isLocked: Boolean = false
)
