package com.example.model

enum class GuideSection(val titleKurdish: String, val titleEnglish: String, val icon: String) {
    OVERVIEW("پوختە و دەسپێک", "Overview", "Home"),
    PANELS("بەشە سەرەکییەکان", "Workspace Panels", "Dashboard"),
    TOOLBAR("ئامرازەکانی Toolbar", "Toolbar Tools", "Build"),
    LAYERS("بەشەکانی Layers", "Layers & Masking", "Layers"),
    ADJUSTMENTS("دەستکاریکردنی ڕەنگ", "Color & Adjustments", "Tune"),
    FILTERS("فلتەر و کاریگەرییەکان", "Filters & Effects", "AutoFix"),
    DESIGN("نووسین و دیزاین", "Typography & Design", "TextFields"),
    FORMATS("فۆرماتەکانی پاشەکەوتکردن", "Export Formats", "Save"),
    DOWNLOAD("داگرتنی ئەپ", "Download App", "Download"),
    QUIZ("تاقیکردنەوەی زانیاری", "Photoshop Quiz", "Quiz")
}

data class PanelItem(
    val id: String,
    val name: String,
    val kurdishName: String,
    val description: String,
    val purpose: String,
    val shortcut: String = ""
)

data class ToolItem(
    val id: String,
    val name: String,
    val kurdishName: String,
    val description: String,
    val shortcut: String,
    val details: String,
    val tips: String
)

data class LayerConcept(
    val id: String,
    val name: String,
    val kurdishName: String,
    val description: String,
    val practicalUse: String
)

data class AdjustmentConcept(
    val id: String,
    val name: String,
    val kurdishName: String,
    val description: String,
    val shortcut: String = "",
    val advice: String
)

data class FilterConcept(
    val id: String,
    val name: String,
    val kurdishName: String,
    val description: String,
    val usageExample: String
)

data class DesignConcept(
    val id: String,
    val name: String,
    val kurdishName: String,
    val description: String,
    val bestPractices: String
)

data class FormatItem(
    val format: String,
    val kurdishUsage: String,
    val layersSupport: Boolean,
    val transparencySupport: Boolean,
    val qualityLevel: String,
    val idealUse: String
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)
