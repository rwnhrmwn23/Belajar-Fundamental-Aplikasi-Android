package com.onedev.dicoding.myunittesting

import junit.framework.Assert.assertEquals
import org.junit.Test

import org.junit.Before
import org.mockito.Mockito.*

class CuboidViewModelTest {

    private lateinit var cuboidViewModel: CuboidViewModel
    private lateinit var cuboidModel: CuboidModel

    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    private val dummyVolume = 504.0
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    @Before
    fun before() {
        cuboidModel = mock(CuboidModel::class.java)
        cuboidViewModel = CuboidViewModel(cuboidModel)
    }

    @Test
    fun testVolume() {
        cuboidModel = CuboidModel()
        cuboidViewModel = CuboidViewModel(cuboidModel)
        cuboidViewModel.save(dummyLength, dummyWidth, dummyHeight)
        val volume = cuboidViewModel.getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testCircumference() {
        cuboidModel = CuboidModel()
        cuboidViewModel = CuboidViewModel(cuboidModel)
        cuboidViewModel.save(dummyLength, dummyWidth, dummyHeight)
        val circumference = cuboidViewModel.getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun testSurfaceArea() {
        cuboidModel = CuboidModel()
        cuboidViewModel = CuboidViewModel(cuboidModel)
        cuboidViewModel.save(dummyLength, dummyWidth, dummyHeight)
        val surfaceArea = cuboidViewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    @Test
    fun testMockVolume() {
        `when`(cuboidViewModel.getVolume()).thenReturn(dummyVolume)
        val volume = cuboidViewModel.getVolume()
        verify(cuboidModel).getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testMockCircumference() {
        `when`(cuboidViewModel.getCircumference()).thenReturn(dummyCircumference)
        val circumference = cuboidViewModel.getCircumference()
        verify(cuboidModel).getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun testMockSurfaceArea() {
        `when`(cuboidViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        val surfaceArea = cuboidViewModel.getSurfaceArea()
        verify(cuboidModel).getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }
}