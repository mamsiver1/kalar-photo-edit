package com.example

import com.example.data.PhotoshopGuideData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PhotoshopGuideDataTest {

    @Test
    fun testPanelsCountAndContent() {
        // Section 1: 10 panels
        assertEquals(10, PhotoshopGuideData.panels.size)
        assertTrue(PhotoshopGuideData.panels.any { it.name == "Menu Bar" })
        assertTrue(PhotoshopGuideData.panels.any { it.name == "Layers Panel" })
    }

    @Test
    fun testToolsCountAndShortcuts() {
        // Section 2: 21 tools from prompt
        assertEquals(21, PhotoshopGuideData.tools.size)
        val moveTool = PhotoshopGuideData.tools.find { it.id == "move" }
        assertNotNull(moveTool)
        assertEquals("V", moveTool?.shortcut)
        assertEquals("گواستنەوەی شتەکان", moveTool?.kurdishName)

        val cropTool = PhotoshopGuideData.tools.find { it.id == "crop" }
        assertNotNull(cropTool)
        assertEquals("C", cropTool?.shortcut)
        assertEquals("بڕینی وێنە", cropTool?.kurdishName)
    }

    @Test
    fun testLayersConcepts() {
        // Section 3: 8 layer operations
        assertEquals(8, PhotoshopGuideData.layers.size)
        assertTrue(PhotoshopGuideData.layers.any { it.name == "Layer Mask" })
        assertTrue(PhotoshopGuideData.layers.any { it.name == "Smart Object" })
    }

    @Test
    fun testAdjustmentsAndFilters() {
        // Section 4 & 5
        assertEquals(12, PhotoshopGuideData.adjustments.size)
        assertEquals(9, PhotoshopGuideData.filters.size)
        assertTrue(PhotoshopGuideData.adjustments.any { it.name.contains("Curves") })
        assertTrue(PhotoshopGuideData.filters.any { it.name == "Neural Filters" })
    }

    @Test
    fun testDesignConceptsAndFormats() {
        // Section 6 & 7
        assertEquals(7, PhotoshopGuideData.designs.size)
        assertEquals(7, PhotoshopGuideData.formats.size)
        assertTrue(PhotoshopGuideData.formats.any { it.format == "PSD" && it.layersSupport })
        assertTrue(PhotoshopGuideData.formats.any { it.format == "PNG" && it.transparencySupport })
    }

    @Test
    fun testDownloadSectionExists() {
        val downloadSection = com.example.model.GuideSection.DOWNLOAD
        assertEquals("داگرتنی ئەپ", downloadSection.titleKurdish)
    }
}
