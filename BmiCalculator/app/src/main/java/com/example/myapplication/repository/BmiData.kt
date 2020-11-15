package com.example.myapplication.repository

import com.example.myapplication.calculation.Unit

data class BmiData(
    val bmiResult: Double,
    val weight: Double,
    val height: Double,
    val date: String,
    val unit: Unit
)