package com.example.myapplication.calculation

import android.os.Parcelable
import com.example.myapplication.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class BmiCategory(val color: Int, val text: Int) : Parcelable {
    UNDERWEIGHT(
        R.color.underweight,
        R.string.underweight
    ),
    NORMAL(
        R.color.normal,
        R.string.normal
    ),
    OVERWEIGHT(
        R.color.overweight,
        R.string.overweight
    ),
    OBESE(
        R.color.obese,
        R.string.obese
    ),
    EXTREMELY_OBESE(
        R.color.extremelyObese,
        R.string.extremely_obese
    ),
    NON_CATEGORY(
        R.color.colorPrimary,
        R.string.empty_text
    )
}