package com.example.myapplication.repository

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val FILE_KEY = "fileKey"
const val ITEM_KEY = "itemKey"

class BmiRepository(activity: Activity) {
    val sharedPreferences = activity.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE)
    val gson = Gson()

    fun readData(): List<BmiData> {
        val json = sharedPreferences.getString(ITEM_KEY, "[]")
        val type = object : TypeToken<List<BmiData>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveDate(bmiData: BmiData) {
        var bmiDataList = readData()
        if (bmiDataList.size == 10) {
            bmiDataList = bmiDataList.drop(1)
        }
        bmiDataList = bmiDataList + bmiData

        val editor = sharedPreferences.edit()
        val json = gson.toJson(bmiDataList)
        editor.putString(ITEM_KEY, json)
        editor.commit()

    }

}