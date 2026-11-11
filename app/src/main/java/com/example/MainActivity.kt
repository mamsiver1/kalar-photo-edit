package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.model.EditorToolType
import com.example.ui.editor.EditorScreen
import com.example.ui.editor.EditorViewModel
import com.example.ui.guide.GuideHomeScreen
import com.example.ui.theme.DarkBg
import com.example.ui.theme.MyApplicationTheme

enum class AppScreen {
    GUIDE,
    EDITOR
}

class MainActivity : ComponentActivity() {

    private val editorViewModel: EditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // Ensure proper Kurdish RTL typography & layout direction
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = DarkBg
                    ) {
                        KalarEditApp(editorViewModel = editorViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun KalarEditApp(
    editorViewModel: EditorViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(AppScreen.GUIDE) }

    when (currentScreen) {
        AppScreen.GUIDE -> {
            GuideHomeScreen(
                onOpenEditor = { toolId ->
                    if (toolId != null) {
                        when (toolId) {
                            "brush", "spot_healing", "clone_stamp", "history_brush" ->
                                editorViewModel.selectTool(EditorToolType.BRUSH)
                            "eraser" ->
                                editorViewModel.selectTool(EditorToolType.ERASER)
                            "type" ->
                                editorViewModel.selectTool(EditorToolType.TEXT)
                            "shape" ->
                                editorViewModel.selectTool(EditorToolType.SHAPES)
                            "eyedropper" ->
                                editorViewModel.selectTool(EditorToolType.EYEDROPPER)
                            "crop" ->
                                editorViewModel.selectTool(EditorToolType.CROP)
                            "layers_panel", "new_layer", "duplicate_layer", "layer_mask", "opacity", "blending_modes" ->
                                editorViewModel.selectTool(EditorToolType.LAYERS)
                            "curves", "levels", "brightness_contrast", "vibrance", "hue_saturation", "color_balance", "black_white" ->
                                editorViewModel.selectTool(EditorToolType.ADJUSTMENTS)
                            "blur_gallery", "sharpen", "noise", "distort", "liquify", "neural_filters" ->
                                editorViewModel.selectTool(EditorToolType.FILTERS)
                            else ->
                                editorViewModel.selectTool(EditorToolType.MOVE)
                        }
                    }
                    currentScreen = AppScreen.EDITOR
                },
                modifier = modifier
            )
        }
        AppScreen.EDITOR -> {
            BackHandler {
                currentScreen = AppScreen.GUIDE
            }
            EditorScreen(
                viewModel = editorViewModel,
                onNavigateBack = {
                    currentScreen = AppScreen.GUIDE
                },
                modifier = modifier
            )
        }
    }
}
