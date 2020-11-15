package com.example.myapplication.calculation

import kotlin.math.roundToInt

const val UNDERWEIGHT_THRESHOLD = 18.5
const val NORMAL_THRESHOLD = 24.9
const val OVERWEIGHT_THRESHOLD = 29.9
const val OBESE_THRESHOLD = 34.9

class BmiCalculator {

    fun count(weight: Double, height: Double, unit: Unit): Double {
        checkDataCorrectness(weight, height)

        var heightInProperForm = if (unit == Unit.KG_CM) heightToMeters(height)
        else height

        val result = (weight * unit.multiplier) / (heightInProperForm * heightInProperForm)
        return roundTo2Digits(result)
    }

    private fun roundTo2Digits(bmiResult: Double): Double {
        return (bmiResult * 100).roundToInt().toDouble() / 100
    }

    private fun heightToMeters(height: Double): Double {
        return height / 100.0
    }

    private fun checkDataCorrectness(weight: Double, height: Double) {
        if (weight <= 0.0)
            throw IllegalArgumentException("Weight cannot be less than or equal to 0.")
        if (height <= 0.0)
            throw IllegalArgumentException("Height cannot be less than or equal to 0.")
    }

    fun interpretResult(bmiResult: Double): BmiCategory {
        return when {
            bmiResult < UNDERWEIGHT_THRESHOLD -> BmiCategory.UNDERWEIGHT
            bmiResult <= NORMAL_THRESHOLD -> BmiCategory.NORMAL
            bmiResult <= OVERWEIGHT_THRESHOLD -> BmiCategory.OVERWEIGHT
            bmiResult <= OBESE_THRESHOLD -> BmiCategory.OBESE
            else -> BmiCategory.EXTREMELY_OBESE
        }
    }
}