package com.example.data

import com.example.model.AdjustmentConcept
import com.example.model.DesignConcept
import com.example.model.FilterConcept
import com.example.model.FormatItem
import com.example.model.LayerConcept
import com.example.model.PanelItem
import com.example.model.QuizQuestion
import com.example.model.ToolItem

object PhotoshopGuideData {

    // 1. بەشە سەرەکییەکانی Photoshop
    val panels = listOf(
        PanelItem(
            id = "menu_bar",
            name = "Menu Bar",
            kurdishName = "شریتی مینیو سەرەکی",
            description = "شریتی باڵای سەرەوە کە گشت فرمانەکانی وەک File, Edit, Image, Layer, Filter لەخۆ دەگرێت.",
            purpose = "بەڕێوەبردنی گشتی پڕۆژە، پاشەکەوتکردن، هاوردەکردنی فایل و ڕێکخستنە سەرەکییەکان.",
            shortcut = "Alt + پیتی یەکەم"
        ),
        PanelItem(
            id = "options_bar",
            name = "Options Bar",
            kurdishName = "شریتی تایبەتمەندییەکان",
            description = "لەژێر شریتی مینیودایە، لەگەڵ گۆڕینی هەر ئامرازێک تایبەتمەندی تایبەت بەو ئامرازە نیشان دەدات.",
            purpose = "گۆڕینی قەبارەی فڵچە، توندی، دۆخی تێکەڵبوون (Mode)، و وردەکارییەکانی ئامرازی کارپێکراو.",
            shortcut = "بەپێی ئامراز دەگۆڕێت"
        ),
        PanelItem(
            id = "toolbar",
            name = "Toolbar",
            kurdishName = "سندووقی ئامرازەکان",
            description = "ستوونی ئامرازەکان لە لای چەپ کە گشت ئامرازەکانی دیزاین، بڕین و دەستکاری تێدایە.",
            purpose = "دەستگەیشتن بە ئامرازەکانی جوڵاندن، کێشان، سڕینەوە، دەق و شێوە ئەندازیارییەکان.",
            shortcut = "کرتەی کورتە پیتی هەر ئامرازێک"
        ),
        PanelItem(
            id = "layers_panel",
            name = "Layers Panel",
            kurdishName = "پەنجەرەی چینەکان",
            description = "دڵی پڕۆژەکەیە، ڕیزبەندی و بینینی چینە جیاوازەکانی دیزاینەکە نیشان دەدات.",
            purpose = "ڕێکخستن، شاردنەوە، سڕینەوە، ڕێکخستنی شەفافییەت و تێکەڵکردنی چینەکان.",
            shortcut = "F7"
        ),
        PanelItem(
            id = "properties_panel",
            name = "Properties Panel",
            kurdishName = "پەنجەرەی تایبەتمەندی چین",
            description = "زانیاری خێرا و کرداری ڕاستەوخۆ دەربارەی چین یان دەق یان شێوەی هەڵبژێردراو دەردەخات.",
            purpose = "گۆڕینی ڕەهەند، ماسک، دەقی زیندوو و لابردنی پاشبنەمای وێنە بە یەک کرتە.",
            shortcut = "Window > Properties"
        ),
        PanelItem(
            id = "color_panel",
            name = "Color Panel",
            kurdishName = "پەنجەرەی ڕەنگ",
            description = "ڕێکخەری ڕەنگەکان بە سیستمەکانی RGB, CMYK, HSB بۆ هەڵبژاردنی ڕەنگی پێشەوە و دواوە.",
            purpose = "دۆزینەوە و دیاریکردنی وردی کۆدی ڕەنگ بۆ فڵچە و پاشبنەما.",
            shortcut = "F6"
        ),
        PanelItem(
            id = "swatches_panel",
            name = "Swatches Panel",
            kurdishName = "پەنجەرەی نموونە ڕەنگەکان",
            description = "کۆکراوەی نموونە ڕەنگە ئامادەکراو و دڵخوازەکان بۆ بەکارهێنانی دووبارە لە پڕۆژەدا.",
            purpose = "پاراستنی یەکگرتوویی ڕەنگەکانی ناسنامەی بینراو و دیزاین بە شێوەیەکی خێرا.",
            shortcut = "Window > Swatches"
        ),
        PanelItem(
            id = "history_panel",
            name = "History Panel",
            kurdishName = "پەنجەرەی مێژووی کردارەکان",
            description = "تۆماری تەواوی هەنگاو و گۆڕانکارییەکانی پێشوو بۆ گەڕانەوە بۆ دۆخی کۆن.",
            purpose = "گەڕانەوە بۆ چەند هەنگاو پێش ئێستا لە حاڵەتی هەڵەکردندا (Undo پێشکەوتوو).",
            shortcut = "Ctrl + Alt + Z / Ctrl + Z"
        ),
        PanelItem(
            id = "adjustments_panel",
            name = "Adjustments Panel",
            kurdishName = "پەنجەرەی ڕێکخستنی ڕەنگ",
            description = "پێشکەشکردنی چینی ڕێکخستن بەبێ لەناوبردنی وێنە بنەڕەتییەکە (Non-destructive).",
            purpose = "زیادکردنی چینەکانی Curves, Levels, Hue/Saturation بۆ جوانکاری ڕەنگ.",
            shortcut = "Window > Adjustments"
        ),
        PanelItem(
            id = "navigator_panel",
            name = "Navigator Panel",
            kurdishName = "پەنجەرەی بینینی گشتی",
            description = "نیشاندانی نەخشەی گچکەی هەموو وێنەکە لە کاتی کارکردن لەسەر بەشێکی زۆر نزیککراوە.",
            purpose = "جوڵانی خێرا لەسەر وردەکارییەکانی وێنە لە کاتی Zoom کردندا.",
            shortcut = "Window > Navigator"
        )
    )

    // 2. ئامرازەکانی Toolbar
    val tools = listOf(
        ToolItem(
            id = "move",
            name = "Move Tool",
            kurdishName = "گواستنەوەی شتەکان",
            description = "گواستنەوەی شتەکان، چینەکان، و دەقەکان لەناو کەنڤاس بە ئاسانی.",
            shortcut = "V",
            details = "بە بەکارهێنانی Auto-Select دەتوانیت ڕاستەوخۆ بە کرتەکردن چینەکە هەڵبژێریت بەبێ سەردانی پەنجەرەی چینەکان.",
            tips = "دوگمەی Ctrl ڕابگرە بۆ دیاریکردنی دووری و سەنتەرکردنی بەشەکان."
        ),
        ToolItem(
            id = "marquee",
            name = "Marquee Tool",
            kurdishName = "هەڵبژاردنی ناوچەی چوارگۆشە یان بازنەیی",
            description = "هەڵبژاردنی بەشێکی دیاریکراوی وێنە بە شێوەی ئەندازیاری چوارگۆشە یان بازنەیی.",
            shortcut = "M",
            details = "گونجاوە بۆ بڕین یان کۆپیکردنی شوێنێکی ڕێکخراوی وێنە بۆ چینی نوێ.",
            tips = "دوگمەی Shift ڕابگرە تا چوارگۆشەی یەکسان یان بازنەی تەواو بکێشیت."
        ),
        ToolItem(
            id = "lasso",
            name = "Lasso Tool",
            kurdishName = "هەڵبژاردنی بە دەستی",
            description = "دەستنیشانکردنی ئازادانەی ناوچەیەک بە جوڵاندنی دەست و ماوس بەبێ بەرتەسککردنەوە.",
            shortcut = "L",
            details = "لەخۆگری سێ شێوازە: ئاسایی، فرەگۆشەیی (Polygonal)، و موگناتیسی (Magnetic).",
            tips = "بۆ شتە لێوار تیژەکان Polygonal Lasso باشترین بژاردەیە."
        ),
        ToolItem(
            id = "object_selection",
            name = "Object Selection Tool",
            kurdishName = "هەڵبژاردنی ئۆبجێکت",
            description = "هەڵبژاردنی زیرەکانەی کەس یان شتێک لەناو وێنە بە یارمەتی ژیری دەستکرد.",
            shortcut = "W",
            details = "تەنها بە کێشانی چوارچێوەیەک بە دەوری شتەکەدا، فۆتۆشۆپ سنوورەکان بە تەواوی جیا دەکاتەوە.",
            tips = "دوگمەی Select Subject لە سەرەوە بە یەک کرتە هەموو کەسەکە دیاری دەکات."
        ),
        ToolItem(
            id = "crop",
            name = "Crop Tool",
            kurdishName = "بڕینی وێنە",
            description = "بڕینی پەراوێزە زیادەکان و ڕێکخستنی ڕێژەی بەرزی و پانی (Aspect Ratio).",
            shortcut = "C",
            details = "دەتوانیت ڕێژەی ١:١ (چوارگۆشە بۆ ئینستاگرام) یان ١٦:٩ (ستۆری و ڤیدیۆ) دیاری بکەیت.",
            tips = "بژاردەی Straighten بەکاربێنە بۆ ڕاستکردنەوەی وێنەی لار."
        ),
        ToolItem(
            id = "frame",
            name = "Frame Tool",
            kurdishName = "دانانی وێنە لە ناو چوارچێوە",
            description = "دروستکردنی چوارچێوەی خاڵی بۆ کێشانی وێنە و گونجاندنی ناوەکی.",
            shortcut = "K",
            details = "کاتێک وێنەیەک دەخەیتە ناوی، ڕاستەوخۆ دەبێتە ماسک و قەبارەی ڕێکدەخات.",
            tips = "زۆر لەبارە بۆ دیزاینی تەختەی کۆلاج و مۆکئاپەکان."
        ),
        ToolItem(
            id = "eyedropper",
            name = "Eyedropper Tool",
            kurdishName = "وەرگرتنی ڕەنگ",
            description = "وەرگرتنی وردی ڕەنگ لە هەر شوێنێکی وێنەکە بێت بۆ بەکارهێنانی لە فڵچە یان دەق.",
            shortcut = "I",
            details = "بە کرتەکردن لەسەر وێنەکە، ڕەنگەکە دەچێتە بەشی Foreground Color.",
            tips = "تەماشای ناوەڕاستی پەڵە ڕەنگەکە بکە و Sample Size بکە بە 5x5 بۆ ڕەنگی ڕاستەقینە."
        ),
        ToolItem(
            id = "spot_healing",
            name = "Spot Healing Brush",
            kurdishName = "لابردنی خاڵ و کەموکوڕی",
            description = "پاککردنەوە و لابردنی خاڵ، زیبکە، لۆچ و کەموکوڕییەکانی دەم و چاو بە خێرایی.",
            shortcut = "J",
            details = "خۆی پیکسڵەکانی چواردەوری خاڵەکە تێکەڵ دەکات بە شێوازێکی زۆر سروشتی.",
            tips = "دڵنیابە بژاردەی Content-Aware لە سەرەوە هەڵبژێردراوە."
        ),
        ToolItem(
            id = "brush",
            name = "Brush Tool",
            kurdishName = "کێشانی بە فڵچە",
            description = "ئامرازی کێشان بە فڵچە، دەستکاری ڕەنگ و کێشانی نەخشە جیاوازەکان.",
            shortcut = "B",
            details = "پشتیوانی هەزاران جۆری فڵچە دەکات و دەتوانیت ئەستووری و نەرمی فڵچەکە دەستکاری بکەیت.",
            tips = "دوگمەی [ و ] لە کیبۆرد بۆ بچووک و گەورەکردنی خێرای فڵچە بەکاربێنە."
        ),
        ToolItem(
            id = "clone_stamp",
            name = "Clone Stamp",
            kurdishName = "کۆپیکردنی بەشێک لە وێنە",
            description = "وەرگرتنی کۆپی لە بەشێکی وێنە و کێشانی لە شوێنێکی تردا.",
            shortcut = "S",
            details = "بە گرتنی دوگمەی Alt سەرچاوەکە هەڵدەبژێریت، پاشان لە شوێنی مەبەست وێنەی پێ دەکێشیتەوە.",
            tips = "بۆ سڕینەوەی کێبڵ و شتی نەخوازراوی پاشبنەما نموونەییە."
        ),
        ToolItem(
            id = "history_brush",
            name = "History Brush",
            kurdishName = "گەڕاندنەوەی دۆخی پێشووی وێنە",
            description = "گەڕاندنەوەی ئەو بەشەی بە فڵچە پێیدا دەهێنیت بۆ دۆخی سەرەتایی یان قۆناغێکی کۆن.",
            shortcut = "Y",
            details = "بەبێ تێکدانی تەواوی وێنەکە، دەتوانیت تەنها ناوچەیەک ببەیتەوە دۆخی سەرەتا.",
            tips = "لە پەنجەرەی History نیشانەی فڵچە دانێ لە تەنیشت ئەو قۆناغەی دەتەوێت بۆی بگەڕێیتەوە."
        ),
        ToolItem(
            id = "eraser",
            name = "Eraser Tool",
            kurdishName = "سڕینەوە",
            description = "سڕینەوەی پیکسڵەکان لەسەر چینەکان و شەفافکردنیان.",
            shortcut = "E",
            details = "لەخۆگری Background Eraser و Magic Eraser ە بۆ سڕینەوەی یەک ڕەنگی پاشبنەما بە یەک کرتە.",
            tips = "لەبری سڕینەوەی هەمیشەیی، بەکارهێنانی Layer Mask باشتر و پارێزراوترە."
        ),
        ToolItem(
            id = "gradient",
            name = "Gradient Tool",
            kurdishName = "دروستکردنی تێکەڵاوی ڕەنگ",
            description = "دروستکردنی ڕەنگی پلەدار لە نێوان دوو یان چەندین ڕەنگدا.",
            shortcut = "G",
            details = "پێشکەشکردنی شێوازی هێڵی (Linear)، بازنەیی (Radial)، گۆشەیی و ئەڵماسی.",
            tips = "دوگمەی Shift ڕابگرە لە کاتی ڕاکێشان تا هێڵەکەت بە تەواوی ڕاست بێت."
        ),
        ToolItem(
            id = "blur",
            name = "Blur Tool",
            kurdishName = "تەمکردنی وێنە",
            description = "نەرمکردن و تەمکردنی وردەکارییە تیژەکان لە شوێنە دەستنیشانکراوەکاندا.",
            shortcut = "R",
            details = "بۆ نەرمکردنی پێستی کەسەکان یان کەمکردنەوەی سەرنج لەسەر بەشێکی پاشبنەما بەکاردێت.",
            tips = "ئاستی Strength کەم بکەرەوە بۆ 20%-30% تا ئەنجامەکەی سروشتی دەرکەوێت."
        ),
        ToolItem(
            id = "dodge",
            name = "Dodge Tool",
            kurdishName = "ڕووناکترکردنەوە",
            description = "ڕووناککردنەوەی بەشە تاریکەکانی وێنە لە ڕێگەی فڵچەوە.",
            shortcut = "O",
            details = "هاوتاکەی Burn Tool ە کە بۆ تاریککردنەوەیە، و Sponge Tool کە بۆ تێری ڕەنگە.",
            tips = "بۆ دروستکردنی کاریگەری Dodge & Burn لەسەر چاو و پێست زۆر کاریگەرە."
        ),
        ToolItem(
            id = "pen",
            name = "Pen Tool",
            kurdishName = "دروستکردنی Path",
            description = "ئامرازی هەرە بەهێزی کێشانی هێڵی ڤێکتەر و دیاریکردنی زۆر وردی تەنەکان.",
            shortcut = "P",
            details = "بە بەکارهێنانی خاڵەکانی Bézier curves دەتوانیت هەر شێوەیەکی ئاڵۆز بە وردی ببڕیت.",
            tips = "دوگمەی Alt ڕابگرە بۆ شکاندنی باڵی چەمانەوەی خاڵەکان."
        ),
        ToolItem(
            id = "type",
            name = "Type Tool",
            kurdishName = "نووسینی دەق",
            description = "نووسینی دەق و پەیامەکان لەسەر دیزاینەکە بە فۆنت و تایبەتمەندی جۆراوجۆر.",
            shortcut = "T",
            details = "پشتیوانی نووسینی ئاسۆیی، ستوونی، لەناو شێوە، و بەدرێژایی ڕێڕەوی هێڵ دەکات.",
            tips = "بە کرتە و ڕاکێشان دەتوانیت چوارچێوەی دەقی (Paragraph box) دروست بکەیت."
        ),
        ToolItem(
            id = "path_selection",
            name = "Path Selection Tool",
            kurdishName = "هەڵبژاردنی Path",
            description = "هەڵبژاردن و گواستنەوەی هێڵە ڤێکتەر و خاڵەکانی کێشراوی ئامرازی Pen.",
            shortcut = "A",
            details = "تیرە ڕەشەکە تەواوی هێڵەکە دەجوڵێنێت، و تیرە سپییەکە (Direct Selection) تەنها یەک خاڵ دەجوڵێنێت.",
            tips = "کورتەپیتی Shift + A بگۆڕە لە نێوان هەردوو تیرەکەدا."
        ),
        ToolItem(
            id = "shape",
            name = "Shape Tool",
            kurdishName = "دروستکردنی شێوە ئەندازیارییەکان",
            description = "کێشانی چوارگۆشە، بازنە، سێگۆشە، هێڵ و شێوە تایبەتەکان (Custom Shapes).",
            shortcut = "U",
            details = "گشت شێوەکان بە سیفەتی ڤێکتەر دروست دەبن و بە گەورەکردن کوالێتی لەدەست نادەن.",
            tips = "لە پەنجەرەی Properties دەتوانیت گۆشەکانی چوارگۆشە خڕ (Rounded Corners) بکەیت."
        ),
        ToolItem(
            id = "hand",
            name = "Hand Tool",
            kurdishName = "جوڵاندنی شوێنی وێنە",
            description = "جوڵاندن و ڕاکێشانی پەڕەی وێنەکە لە کاتێکدا زووم کراوە بەبێ گۆڕینی چینەکان.",
            shortcut = "H",
            details = "کارئاسانی دەکات بۆ گەشتکردن بەناو بەشە جیاوازەکانی تابلۆکەدا.",
            tips = "لە کاتی بەکارهێنانی هەر ئامرازێک بیت، تەنها دوگمەی Spacebar ڕابگرە تا دەبێتە Hand Tool!"
        ),
        ToolItem(
            id = "zoom",
            name = "Zoom Tool",
            kurdishName = "گەورە و بچووککردنەوە",
            description = "نزیککردنەوە و دوورخستنەوەی وێنەکە بۆ کارکردن لەسەر وردترین پیکسڵەکان.",
            shortcut = "Z",
            details = "کرتە گەورەی دەکات، بە گرتنی Alt کرتەکردن بچووکی دەکاتەوە.",
            tips = "دوگمەی Ctrl + 0 لێبدە بۆ پڕکردنەوەی پەڕەکە بە تەواوی شاشە (Fit on Screen)."
        )
    )

    // 3. بەشەکانی Layers
    val layers = listOf(
        LayerConcept(
            id = "new_layer",
            name = "New Layer",
            kurdishName = "زیادکردنی چینێکی نوێ",
            description = "دروستکردنی چینێکی شەفافی خاڵی لەسەرووی چینەکانی تر بۆ ئەنجامدانی کاری سەربەخۆ.",
            practicalUse = "دەتوانیت بە جیا ڕەنگ یان دەق بکێشیت بەبێ ئەوەی دەستکاری وێنەی بنەڕەتی بکرێت."
        ),
        LayerConcept(
            id = "duplicate_layer",
            name = "Duplicate Layer",
            kurdishName = "کۆپیکردنی چین",
            description = "دروستکردنی وێنەیەکی دەقاودەقی چینی هەڵبژێردراو لە کورتترین کاتدا.",
            practicalUse = "کۆپیکردنی وێنە پێش دەستکاریکردن تا وەک پشتیوانی (Backup) بمێنێتەوە (Ctrl + J)."
        ),
        LayerConcept(
            id = "delete_layer",
            name = "Delete Layer",
            kurdishName = "سڕینەوەی چین",
            description = "سڕینەوەی یەکجاری ئەو چینەی چیتر پێویستت پێی نییە بۆ پاکڕاگرتنی پڕۆژە.",
            practicalUse = "ڕاکێشانی چینەکە بۆ سەتڵی خۆڵ یان لێدانی دوگمەی Delete لە کیبۆرد."
        ),
        LayerConcept(
            id = "layer_mask",
            name = "Layer Mask",
            kurdishName = "شاراوەکردن یان دەرخستنی بەشێک",
            description = "ماسکێک کە ڕێگە دەدات بە ڕەنگی ڕەش و سپی بەشێک لە چینەکە بشاریتەوە بەبێ سڕینەوە.",
            practicalUse = "ڕەنگی ڕەش دەیشارێتەوە، ڕەنگی سپی دەریدەخات؛ هەر کات بتەوێت دەتوانیت بیگەڕێنیتەوە."
        ),
        LayerConcept(
            id = "opacity",
            name = "Opacity",
            kurdishName = "کەمکردنەوەی ڕوونی چین",
            description = "کۆنتڕۆڵکردنی ئاستی شەفافییەت و دەرکەوتنی چین لە 0% (شەفافی ڕەها) بۆ 100% (تەواو دیار).",
            practicalUse = "نەرمکردنی تێکەڵبوونی لۆگۆ یان سێبەر و ئاوێتەکردنی جوان لەگەڵ پاشبنەمادا."
        ),
        LayerConcept(
            id = "blending_modes",
            name = "Blending Modes",
            kurdishName = "تێکەڵکردنی چینەکان",
            description = "یاسای بیرکاری بۆ تێکەڵکردنی ڕەنگی چینەکە لەگەڵ چینەکانی ژێرەوەیدا.",
            practicalUse = "بۆ نموونە Multiply بەشە ڕووناکەکان لادەبات، Screen بەشە تاریکەکان لادەبات، و Overlay کۆنتراست بەرزدەکاتەوە."
        ),
        LayerConcept(
            id = "group",
            name = "Group",
            kurdishName = "کۆکردنەوەی چەند چینێک",
            description = "کۆکردنەوەی چەندین چین لەناو پوشەیەکدا (Folder) بۆ ڕێکخستن و کەمکردنەوەی قەرەباڵغی.",
            practicalUse = "ڕێکخستنی بەشەکانی دیزاین وەک مینیو، دەقەکان، وێنەکان؛ کورتکراوەکەی (Ctrl + G)."
        ),
        LayerConcept(
            id = "smart_object",
            name = "Smart Object",
            kurdishName = "پاراستنی کوالێتی لە کاتی گۆڕانکاری",
            description = "گۆڕینی چین بۆ تەنێکی زیرەک کە زانیاری ڕەسەنی وێنە دەپارێزێت هەرچەندە بچووک و گەورە بکرێت.",
            practicalUse = "ئەگەر چینی ئاسایی بچووک بکەیت پاشان گەورەی بکەیت کوالێتی دادەبەزێت، بەڵام Smart Object سەد لە سەد دەمێنێتەوە."
        )
    )

    // 4. بەشەکانی دەستکاریکردنی ڕەنگ و وێنە
    val adjustments = listOf(
        AdjustmentConcept(
            id = "brightness_contrast",
            name = "Brightness / Contrast",
            kurdishName = "ڕووناکی و جیاوازی تۆخی",
            description = "ڕێکخستنی ئاستی ڕۆشنایی گشتی و دەرخستنی جیاوازی نێوان ڕووناک و تاریکەکان.",
            shortcut = "Image > Adjustments",
            advice = "بە ڕێژەی کەم بەکاربێنە تا پیکسڵەکان نەسووتێن."
        ),
        AdjustmentConcept(
            id = "levels",
            name = "Levels",
            kurdishName = "ئاستەکانی ڕووناکی (Levels)",
            description = "کۆنتڕۆڵکردنی سێ خاڵی سەرەکی: تۆخی (Shadows)، نیوەڕەنگ (Midtones)، و گەشاوەیی (Highlights).",
            shortcut = "Ctrl + L",
            advice = "بە هێڵکاری Histogram یارمەتیت دەدات بۆ دەرخستنی ڕەنگی ڕاستەقینە."
        ),
        AdjustmentConcept(
            id = "curves",
            name = "Curves",
            kurdishName = "چەماوەکانی ڕەنگ و ڕووناکی",
            description = "بەهێزترین ئامرازی کۆنتڕۆڵکردنی تۆن و ڕەنگ لە تەواوی فۆتۆشۆپدا لە ڕێگەی کێشانی چەماوەوە.",
            shortcut = "Ctrl + M",
            advice = "چەماوەی لە شێوەی پیتی 'S' کۆنتراستێکی سینەمایی سەرسوڕهێنەر بە وێنە دەبەخشێت."
        ),
        AdjustmentConcept(
            id = "exposure",
            name = "Exposure",
            kurdishName = "بەرکەوتنی ڕووناکی",
            description = "ڕێکخستنی ئەو ڕووناکییەی لە کاتی گرتنی وێنەکە چووەتە ناو هاوێنەی کامێراکە.",
            advice = "چارەسەری ئەو وێنانە دەکات کە زۆر تاریک یان زۆر ڕووناک دەرچوون."
        ),
        AdjustmentConcept(
            id = "vibrance",
            name = "Vibrance",
            kurdishName = "زیندوویی زیرەکانەی ڕەنگ",
            description = "تێرکردنی ئەو ڕەنگانەی کز دەرکەوتوون بەبێ ئەوەی ڕەنگی پێستی مرۆڤ زیاد لە پێویست پرتەقاڵی بکات.",
            advice = "هەمیشە لەپێش Saturation بەکاری بهێنە بۆ وێنەی کەسەکان."
        ),
        AdjustmentConcept(
            id = "hue_saturation",
            name = "Hue / Saturation",
            kurdishName = "جۆری ڕەنگ و تێری ڕەنگ",
            description = "گۆڕینی ڕەنگی بنەڕەتی شتەکان (Hue) و ئاستی تێری و بێڕەنگبوونیان (Saturation).",
            shortcut = "Ctrl + U",
            advice = "دەتوانیت تەنها ڕەنگێکی دیاریکراو وەک سوور یان شین بگۆڕیت بۆ ڕەنگێکی تر."
        ),
        AdjustmentConcept(
            id = "color_balance",
            name = "Color Balance",
            kurdishName = "هاوسەنگی ڕەنگەکان",
            description = "کۆنتڕۆڵکردنی ڕێژەی ڕەنگە سەرەکییەکان بەرامبەر ڕەنگە لاوەکییەکان (سوور بەرامبەر شینی ئاسمانی، زەرد بەرامبەر شین).",
            shortcut = "Ctrl + B",
            advice = "نموونەییە بۆ ڕێکخستنی گەرمی و ساردی وێنەی تەلار و سروشت."
        ),
        AdjustmentConcept(
            id = "black_white",
            name = "Black & White",
            kurdishName = "ڕەش و سپیکردنی پێشکەوتوو",
            description = "گۆڕینی وێنە بۆ ڕەش و سپی بە پێدانی کۆنتڕۆڵی تەواو لەسەر تۆخی هەر ڕەنگێکی وێنە بنەڕەتییەکە.",
            shortcut = "Alt + Shift + Ctrl + B",
            advice = "دەتوانیت ئاسمانی شین تاریک بکەیت بۆ دەرخستنی هەوری سپی دراماتیکی."
        ),
        AdjustmentConcept(
            id = "photo_filter",
            name = "Photo Filter",
            kurdishName = "فلتەری وێنەگری",
            description = "دانانی فلتەری هاوشێوەی هاوێنەی کامێرای فۆتۆگرافی بۆ بەخشینی گەرمی (Warming) یان ساردی (Cooling).",
            advice = "فلتەری گەرم (85) هەستی خۆرهەڵاتن و کەشوهەوای خۆش دەبەخشێت."
        ),
        AdjustmentConcept(
            id = "gradient_map",
            name = "Gradient Map",
            kurdishName = "نەخشەی ڕەنگی پلەدار",
            description = "دانانی ڕەنگی جیاواز بەپێی ئاستی ڕووناکی وێنەکە لە تاریکترینەوە بۆ ڕووناکترین خاڵ.",
            advice = "بۆ مۆدێلی پۆستەر و ڕەنگاوڕەنگکردنی مۆدێرنی هونەری زۆر بەناوبانگە."
        ),
        AdjustmentConcept(
            id = "selective_color",
            name = "Selective Color",
            kurdishName = "ڕەنگکردنی هەڵبژێردراو",
            description = "دەستکاریکردنی تەنها ڕەنگێکی تایبەت لە ناو وێنەکە بە سیستمی چاپی CMYK بە وردییەکی بێهاوتا.",
            advice = "زۆرجار لە ڕێکخستنی ڕەنگی فەرمی چاپ و فۆتۆگرافی پیشەیی بەکاردێت."
        ),
        AdjustmentConcept(
            id = "camera_raw",
            name = "Camera Raw Filter",
            kurdishName = "فلتەری گەورەی Camera Raw",
            description = "ستۆدیۆیەکی تەواوی ڕێکخستنی وێنە کە بەهێزترین توانا بۆ سپیکردن، گۆڕینی ڕووناکی و ڕەنگ پێشکەش دەکات.",
            shortcut = "Ctrl + Shift + A",
            advice = "ستانداردی ژمارە یەکی فۆتۆگرافەرانی جیهانە پێش هەر دەستکارییەکی تر."
        )
    )

    // 5. بەشەکانی فلتەر و کاریگەرییەکان
    val filters = listOf(
        FilterConcept(
            id = "blur_gallery",
            name = "Blur Gallery",
            kurdishName = "پێشانگای تەمکردن و لێڵکردن",
            description = "کۆمەڵێک فلتەری پێشکەوتوو وەک Field Blur, Iris Blur, و Tilt-Shift بۆ دروستکردنی بۆکێ (Bokeh) و لێڵکردنی پاشبنەما.",
            usageExample = "دروستکردنی کاریگەری کامێرای گرانبەها لەسەر پاشبنەمای وێنەی کەسەکان."
        ),
        FilterConcept(
            id = "sharpen",
            name = "Sharpen",
            kurdishName = "تیژکردن و ڕووناککردنەوەی وردەکاری",
            description = "زیادکردنی کۆنتراستی پەراوێزەکان بۆ دەرخستنی وردەکاریی زیاتر لە جلوبەرگ، موو و تێکستەردا.",
            usageExample = "فلتەری Unsharp Mask یان Smart Sharpen کوالێتی پیشەیی دەبەخشن."
        ),
        FilterConcept(
            id = "noise",
            name = "Noise",
            kurdishName = "زیادکردنی دەنکۆڵەی فیلم",
            description = "زیادکردنی دەنکۆڵەی هونەری (Add Noise) بۆ بەخشینی هەستی فیلمی ئەسڵی یان نەهێشتنی پلەبەندی ناتەواوی ڕەنگ (Color Banding).",
            usageExample = "بەخشینی ڕووکاری کلاسیکی و ڤینتیج بە وێنەی نوێ."
        ),
        FilterConcept(
            id = "distort",
            name = "Distort",
            kurdishName = "شێواندن و چەمانەوە",
            description = "شێواندنی وێنە بە شێوەی شەپۆلی، پێچانەوە (Twirl)، گۆیی (Spherize) یان کەوانەیی.",
            usageExample = "دروستکردنی بازنەی ڕەنگ و تەقینەوەی خەیاڵی بۆ پاشبنەمای پۆستەر."
        ),
        FilterConcept(
            id = "pixelate",
            name = "Pixelate",
            kurdishName = "پیکسڵکردن و مۆزایک",
            description = "گۆڕینی وێنەکە بۆ بلۆکی ڕەنگی مۆزایکی یان پیکسڵکراو و خاڵ خاڵ.",
            usageExample = "شاردنەوەی پلاک یان دەم و چاوی نەناسراو و دروستکردنی شێوازی یارییە کۆنەکان (Pixel Art)."
        ),
        FilterConcept(
            id = "render",
            name = "Render",
            kurdishName = "دروستکردنی تیشک و هەور",
            description = "دروستکردنی شتی سروشتی لە بەتاڵیدا وەک دروستکردنی هەور (Clouds) و تیشکی خۆر (Lens Flare).",
            usageExample = "زیادکردنی تیشکێکی جوانی خۆر کە لە نێوان پەنجەرە یان درەختەوە دێتە ژوورەوە."
        ),
        FilterConcept(
            id = "liquify",
            name = "Liquify",
            kurdishName = "نەرمکردن و شلکردنی وێنە",
            description = "ڕاکێشان، پاڵنان، گەورەکردن و بچووککردنەوەی بەشەکانی وێنە هاوشێوەی خمیرە یان شلە.",
            usageExample = "ڕێکخستنی شێوازی لەش، ڕاستکردنەوەی لۆچی جلوبەرگ، و دەستکاریکردنی بزەی دەم و چاو (Face-Aware Liquify)."
        ),
        FilterConcept(
            id = "neural_filters",
            name = "Neural Filters",
            kurdishName = "فلتەرە زیرەکەکانی ژیری دەستکرد",
            description = "فلتەرە پێشکەوتووەکانی ئەدۆبی بە بەکارهێنانی AI بۆ گۆڕینی تەمەن، دەربڕینی دەم و چاو، پاککردنەوەی پێست بە یەک کرتە.",
            usageExample = "گۆڕینی ئاراستەی سەیرکردنی چاو یان گۆڕینی کەشوهەوای وێنە لە هاوینەوە بۆ زستان!"
        ),
        FilterConcept(
            id = "filter_gallery",
            name = "Filter Gallery",
            kurdishName = "پێشانگای فلتەرە هونەرییەکان",
            description = "پێشانگایەکی گەورە لە فلتەرە کلاسیکییەکان وەک کێشانی بە قەڵەم، زەیتی، ڕەنگاوڕەنگ و کاریکاتێری.",
            usageExample = "گۆڕینی وێنەی فۆتۆگرافی بۆ کێشراوێکی تابلۆیی مێژوویی."
        )
    )

    // 6. بەشەکانی نووسین و دیزاین
    val designs = listOf(
        DesignConcept(
            id = "character",
            name = "Character",
            kurdishName = "ڕێکخستنی فۆنت و قەبارە",
            description = "دیاریکردنی جۆری فۆنت، قەبارە (Size)، دووری نێوان پیتەکان (Tracking)، و دووری نێوان دێڕەکان (Leading).",
            bestPractices = "بۆ زمانی کوردی فۆنتە ڕوون و خوێنراوەکان بەکاربێنە و دووری دێڕەکان کەمێک زیاتر بکە تا پیتە خاڵدارەکان تێکەڵ نەبن."
        ),
        DesignConcept(
            id = "paragraph",
            name = "Paragraph",
            kurdishName = "ڕێکخستنی پاراگراف",
            description = "دیاریکردنی ئاراستەی نووسین لە ڕاست بۆ چەپ (RTL)، یەکسانکردنی لێوارەکان (Justify)، و دەستپێکی دێڕ.",
            bestPractices = "هەمیشە دڵنیابە ئاراستەی پیتە کوردییەکان لە ڕاستەوە بۆ چەپە تا پیتەکان پێکەوە بلکێن."
        ),
        DesignConcept(
            id = "text_effects",
            name = "Text Effects",
            kurdishName = "کاریگەری لەسەر نووسین (Layer Styles)",
            description = "زیادکردنی کاریگەرییە سێ ڕەهەندییەکان وەک سێبەر (Drop Shadow)، هێڵی دەوری پیت (Stroke)، بریقە (Outer Glow)، و بەرزکردنەوە (Bevel & Emboss).",
            bestPractices = "سێبەری کەم و نەرم هەمیشە دیزاینەکە سەرنجڕاکێشتر و پیشەییتر دەردەخات."
        ),
        DesignConcept(
            id = "shapes",
            name = "Shapes",
            kurdishName = "شێوە ئەندازیارییەکان",
            description = "دروستکردنی شێوەکانی چوارگۆشە، بازنە، فرەگۆشە و هێڵەکان بە ڕەنگی پڕکراوە (Fill) و هێڵی دەوروبەر (Stroke).",
            bestPractices = "شێوەکان دەتوانرێن وەک کارت و پاشبنەمای دەقەکان بۆ جیاکردنەوەی بابەتەکان بەکاربهێنرێن."
        ),
        DesignConcept(
            id = "paths",
            name = "Paths",
            kurdishName = "ڕێڕەوی دیزاین",
            description = "هێڵە ڤێکتەرییە تەواوەکان کە دەتوانرێن بۆ بڕینی وێنە یان نووسینی دەق بەدرێژایی هێڵێکی چەماوە بەکاربهێنرێن.",
            bestPractices = "بە ئامرازی Pen ڕێڕەو بکێشە پاشان بە Type Tool کلیکی لەسەر بکە تا دەق بە چەماوەیی بنووسێت."
        ),
        DesignConcept(
            id = "gradients",
            name = "Gradients",
            kurdishName = "تێکەڵاوی ڕەنگ",
            description = "دروستکردنی تێکەڵاوی مۆدێرنی دوو یان سێ ڕەنگ لە پاشبنەما و دوگمە و دەقەکاندا.",
            bestPractices = "تێکەڵکردنی ڕەنگە نزیکەکان (وەک نیلی بۆ مۆر، یان پەمەیی بۆ پرتەقاڵی) هەستێکی سەردەمیانە دەبەخشێت."
        ),
        DesignConcept(
            id = "patterns",
            name = "Patterns",
            kurdishName = "نەخش و دووبارەبوونەوەی شێوە",
            description = "تایڵ و نەخشی دووبارەبووەوە وەک تۆڕ (Grid)، پەڵە ڕەنگ، و نەخشە ئەندازیارییەکان بۆ دەوڵەمەندکردنی پاشبنەما.",
            bestPractices = "نەخشی کەم ڕووناکی بۆ پشت بەشە سەرەکییەکان بەکاربێنە تا سەرنج لەسەر بابەتەکە نەبات."
        )
    )

    // 7. فۆرماتەکانی پاشەکەوتکردن
    val formats = listOf(
        FormatItem(
            format = "PSD",
            kurdishUsage = "پڕۆژەی Photoshop بە چینەکان",
            layersSupport = true,
            transparencySupport = true,
            qualityLevel = "بێ لەدەستدان (Lossless)",
            idealUse = "فایلی ئەسڵی کارکردنە، هەموو چینەکان، دەقە زیندووەکان، ماسک و کاریگەرییەکان دەپارێزێت."
        ),
        FormatItem(
            format = "PSB",
            kurdishUsage = "فایلە زۆر گەورەکان (Large Document)",
            layersSupport = true,
            transparencySupport = true,
            qualityLevel = "بێ لەدەستدان (Lossless)",
            idealUse = "بۆ ئەو دیزاینە گەورانەی قەبارەیان لە ٢ گێگابایت تێدەپەڕێت یان پیکسڵەکانیان زیاترە لە ٣٠,٠٠٠ پیکسڵ."
        ),
        FormatItem(
            format = "JPG / JPEG",
            kurdishUsage = "وێنەی ئاسایی و تۆڕە کۆمەڵایەتییەکان",
            layersSupport = false,
            transparencySupport = false,
            qualityLevel = "پەستێنراو (Compressed)",
            idealUse = "ستانداردی بڵاوکردنەوەیە لە ماڵپەڕ، ئینستاگرام، فەیسبووک بەهۆی کەمی قەبارەی فایلەکەی."
        ),
        FormatItem(
            format = "PNG",
            kurdishUsage = "وێنەی پشتەوەی شەفاف",
            layersSupport = false,
            transparencySupport = true,
            qualityLevel = "کوالێتی زۆر بەرز (Lossless)",
            idealUse = "بۆ لۆگۆ، ئایکۆن، مۆرک، و وێنەی بێ پاشبنەما بۆ دانانی لەسەر ڤیدیۆ یان پۆستەری تر."
        ),
        FormatItem(
            format = "TIFF",
            kurdishUsage = "چاپ و کوالێتی بەرز",
            layersSupport = true,
            transparencySupport = true,
            qualityLevel = "کوالیتی باڵای پیشەیی",
            idealUse = "ستانداردی دەزگاکانی چاپەمەنی و فۆتۆگرافەرەکانە کە هیچ زیانێک بە پیکسڵ ناگەیەنێت."
        ),
        FormatItem(
            format = "PDF",
            kurdishUsage = "بەڵگە و چاپکردنی ڕاستەوخۆ",
            layersSupport = true,
            transparencySupport = true,
            qualityLevel = "تێکەڵەی ڤێکتەر و پیکسڵ",
            idealUse = "ناردن بۆ چاپخانەکان، پێشکەشکردنی برۆشوور و بەڵگەنامەی بازرگانی بە ڕوونی تەواو."
        ),
        FormatItem(
            format = "GIF",
            kurdishUsage = "وێنەی جوڵاو",
            layersSupport = true,
            transparencySupport = true,
            qualityLevel = "256 ڕەنگ",
            idealUse = "بۆ ئەنیمەیشنی کورت، میم، و ئایکۆنی جوڵاو لەناو پەیام و وێبسایتەکاندا."
        )
    )

    // Quiz Questions for testing Photoshop mastery
    val quizQuestions = listOf(
        QuizQuestion(
            id = 1,
            question = "کورتەپیتی کێشانی شێوەی ئەندازیاری وەک بازنە و چوارگۆشە (Shape Tool) چییە لە کیبۆرد؟",
            options = listOf("U", "M", "V", "B"),
            correctIndex = 0,
            explanation = "دوگمەی U لە کیبۆرد بۆ ئامرازەکانی Shape و شێوە ئەندازیارییەکانە."
        ),
        QuizQuestion(
            id = 2,
            question = "کام لەم فۆرماتانە پشتگیری پاشبنەمای شەفاف (Transparent Background) دەکات؟",
            options = listOf("JPEG", "PNG", "BMP", "RAW"),
            correctIndex = 1,
            explanation = "فۆرماتی PNG توانای پاراستنی پاشبنەمای شەفافی هەیە بەبێ لەدەستدانی کوالێتی."
        ),
        QuizQuestion(
            id = 3,
            question = "سوودی سەرەکی Smart Object چییە کاتێک لەسەر وێنەیەک کار دەکەیت؟",
            options = listOf(
                "پاراستنی کوالێتی و زانیاری ڕەسەن لە کاتی گەورە و بچووککردنەوەدا",
                "ڕەش و سپیکردنی وێنەکە",
                "کەمکردنەوەی قەبارەی ڕام بۆ نیوە",
                "سڕینەوەی چینەکان"
            ),
            correctIndex = 0,
            explanation = "تەنی زیرەک (Smart Object) زانیاری ڕەسەنی وێنە دەپارێزێت و ناهێڵێت پیکسڵەکان تێکبچن لە کاتی Resize دا."
        ),
        QuizQuestion(
            id = 4,
            question = "لە Layer Mask، ڕەنگی ڕەش چ کارێک دەکات لەسەر چینەکە؟",
            options = listOf(
                "بەشەکە دەشارێتەوە",
                "بەشەکە بە تەواوی دەسڕێتەوە",
                "بەشەکە دەردەخات",
                "ڕەنگەکە دەکاتە سێبەر"
            ),
            correctIndex = 0,
            explanation = "لە یاسای ماسکدا: ڕەش دەشارێتەوە (Hides) و سپی دەردەخات (Reveals)."
        ),
        QuizQuestion(
            id = 5,
            question = "کام فلتەرە زیرەکە پشت بە ژیری دەستکرد (AI) دەبەستێت بۆ گۆڕینی تەمەن یان دەربڕینی دەم و چاو؟",
            options = listOf("Neural Filters", "Blur Gallery", "Pixelate", "Render Clouds"),
            correctIndex = 0,
            explanation = "فلتەرەکانی Neural Filters بە مۆدێلی AI دەتوانن پێست ڕێکبخەن، تەمەن زیاد یان کەم بکەن بە شێوازێکی بڕوانەکراو."
        )
    )
}
