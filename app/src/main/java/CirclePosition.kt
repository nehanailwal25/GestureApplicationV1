package com.example.GestureApplicationV1_test

import android.graphics.*

data class CirclePosition (
        val x: Float,
        val y: Float,
        val maxX: Float = 0f,
        val maxY: Float = 0f,
        val minX: Float = 0f,
        val minY: Float = 0f,
        val radius: Float = 0f,
        val isFilled: Boolean,
        val paint: Paint,
        val type: Int  // 1 = circle, 2 = line, 3 = triangle, 4 = square
)