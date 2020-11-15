package com.example.myapplication

import com.example.myapplication.calculation.BmiCalculator
import com.example.myapplication.calculation.BmiCategory
import com.example.myapplication.calculation.Unit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

const val TOLERANCE = 0.01
const val CORRECT_HEIGHT_CM = 160.0
const val CORRECT_HEIGHT_IN = 65.5
const val CORRECT_WEIGHT_KG = 60.45
const val CORRECT_WEIGHT_LB = 120.4

class BmiCalculatorTest : StringSpec({
    val bmiCalculator = BmiCalculator()

    "count() should return correct BMI result (metric system)"{
        forAll(
            table(
                headers("weight", "height", "expectedBmiResult"),
                row(40.5, 175.25, 13.19),
                row(51.0, 150.0, 22.67),
                row(100.1, 203.0, 24.29),
                row(82.0, 179.0, 25.59),
                row(82.0, 163.4, 30.71)
            )
        ) { weight: Double, height: Double, expectedBmiResult: Double ->
            bmiCalculator.count(weight, height, Unit.KG_CM) shouldBe expectedBmiResult.plusOrMinus(
                TOLERANCE
            )
        }
    }

    "count() should return correct BMI result (imperial system)"{
        forAll(
            table(
                headers("weight", "height", "expectedBmiResult"),
                row(98.5, 70.25, 14.03),
                row(112.0, 61.0, 21.16),
                row(220.1, 79.0, 24.79),
                row(182.0, 64.5, 30.75),
                row(165.0, 70.47, 23.36)
            )
        ) { weight: Double, height: Double, expectedBmiResult: Double ->
            bmiCalculator.count(weight, height, Unit.LB_IN) shouldBe expectedBmiResult.plusOrMinus(
                TOLERANCE
            )
        }
    }

    "interpretResult() should return correct BMI category"{
        forAll(
            table(
                headers("bmiResult", "expectedBmiCategory"),
                row(16.25, BmiCategory.UNDERWEIGHT),
                row(18.5, BmiCategory.NORMAL),
                row(21.0, BmiCategory.NORMAL),
                row(26.0, BmiCategory.OVERWEIGHT),
                row(25.0, BmiCategory.OVERWEIGHT),
                row(31.0, BmiCategory.OBESE),
                row(30.0, BmiCategory.OBESE),
                row(42.4, BmiCategory.EXTREMELY_OBESE),
                row(35.0, BmiCategory.EXTREMELY_OBESE)
            )
        ) { bmiResult: Double, expectedBmiCategory: BmiCategory ->
            bmiCalculator.interpretResult(bmiResult) shouldBe expectedBmiCategory
        }

    }

    "count() should throw an IllegalArgumentException when weight is less than or equal 0 (metric system)"{
        forAll(
            table(
                headers("weight"),
                row(0.0),
                row(-50.0)
            )
        ) { weight: Double ->
            shouldThrow<IllegalArgumentException> {
                bmiCalculator.count(weight, CORRECT_HEIGHT_CM, Unit.KG_CM)
            }
        }
    }

    "count() should throw an IllegalArgumentException when height is less than or equal 0 (metric system)"{
        forAll(
            table(
                headers("height"),
                row(0.0),
                row(-160.0)
            )
        ) { height: Double ->
            shouldThrow<IllegalArgumentException> {
                bmiCalculator.count(CORRECT_WEIGHT_KG, height, Unit.KG_CM)
            }
        }
    }

    "count() should throw an IllegalArgumentException when weight is less than or equal 0 (imperial system)"{
        forAll(
            table(
                headers("weight"),
                row(0.0),
                row(-50.0)
            )
        ) { weight: Double ->
            shouldThrow<IllegalArgumentException> {
                bmiCalculator.count(weight, CORRECT_HEIGHT_IN, Unit.LB_IN)
            }
        }
    }

    "count() should throw an IllegalArgumentException when height is less than or equal 0 (imperial system)"{
        forAll(
            table(
                headers("height"),
                row(0.0),
                row(-160.0)
            )
        ) { height: Double ->
            shouldThrow<IllegalArgumentException> {
                bmiCalculator.count(CORRECT_WEIGHT_LB, height, Unit.LB_IN)
            }
        }
    }

    "count() should throw an IllegalArgumentException when height and weight is less than or equal 0 (metric system)"{
        forAll(
            table(
                headers("weight", "height"),
                row(0.0, 0.0),
                row(-50.0, -150.0),
                row(50.0, -150.0),
                row(-50.0, 150.0)
            )
        ) { weight: Double, height: Double ->
            shouldThrow<IllegalArgumentException> {
                bmiCalculator.count(weight, height, Unit.KG_CM)
            }
        }
    }

    "count() should throw an IllegalArgumentException when height and weight is less than or equal 0 (imperial system)"{
        forAll(
            table(
                headers("weight", "height"),
                row(0.0, 0.0),
                row(-100.0, -70.0),
                row(100.0, -70.0),
                row(-100.0, 70.0)
            )
        ) { weight: Double, height: Double ->
            shouldThrow<IllegalArgumentException> {
                bmiCalculator.count(weight, height, Unit.LB_IN)
            }
        }
    }

})