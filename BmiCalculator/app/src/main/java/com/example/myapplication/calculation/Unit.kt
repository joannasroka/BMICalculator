package com.example.myapplication.calculation

import android.os.Parcelable
import com.example.myapplication.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Unit(
    val multiplier: Int,
    val weight: Int,
    val height: Int
) : Parcelable {
    KG_CM(
        1,
        R.string.unit_kg,
        R.string.unit_cm
    ),
    LB_IN(
        703,
        R.string.unit_lb,
        R.string.unit_in
    )
}
