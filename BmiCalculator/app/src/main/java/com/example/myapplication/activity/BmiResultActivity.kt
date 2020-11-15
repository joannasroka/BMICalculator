package com.example.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.calculation.BmiCategory
import com.example.myapplication.databinding.ActivityBmiResultBinding

const val BMI_CATEGORY_KEY = "BMI_CATEGORY_RESULT"
const val BMI_RESULT_KEY = "BMI_RESULT"

class BmiResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityBmiResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiResultBinding.inflate(layoutInflater)

        val bundle = intent.extras
        if (bundle != null) {
            binding.bmiCategory = bundle.getParcelable<BmiCategory>(BMI_CATEGORY_KEY)!!
            binding.bmiResult = bundle.getDouble(BMI_RESULT_KEY).toString()
        }

        binding.executePendingBindings()
        setContentView(binding.root)

    }
}