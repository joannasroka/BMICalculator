// Joanna Sroka 246756
// Testowane na Xiaomi Redmi 6

package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.calculation.BmiCalculator
import com.example.myapplication.calculation.BmiCategory
import com.example.myapplication.calculation.Unit
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.BmiData
import com.example.myapplication.repository.BmiRepository
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_CODE = 0
const val UNIT_KEY = "UNIT"
const val DATE_FORMAT = "dd-MM-yyyy"

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var result: Double = 0.0
    var unitSwitch: Unit = Unit.KG_CM
    var bmiCategoryResult: BmiCategory = BmiCategory.NON_CATEGORY
    lateinit var bmiRepository: BmiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        bmiRepository = BmiRepository(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        restoreUnitState(savedInstanceState)
        restoreBmiResult(savedInstanceState)
        restoreBmiCategory(savedInstanceState)

        binding.executePendingBindings()
        setContentView(binding.root)
        setSupportActionBar(binding.menu)
        setListenerOnBmiResult()
    }

    private fun setListenerOnBmiResult() {
        binding.bmiTV.setOnClickListener { _ ->
            if (result > 0.0) {
                val bundle = Bundle()
                bundle.putParcelable(BMI_CATEGORY_KEY, bmiCategoryResult)
                bundle.putDouble(BMI_RESULT_KEY, result)
                val intent = Intent(baseContext, BmiResultActivity::class.java)
                intent.putExtras(bundle)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivityForResult(
                    intent,
                    REQUEST_CODE
                )
            }

        }
    }

    private fun restoreBmiCategory(savedInstanceState: Bundle?) {
        savedInstanceState?.getParcelable<BmiCategory>(BMI_CATEGORY_KEY)?.let {
            bmiCategoryResult = it
        }
        binding.bmiCategory = bmiCategoryResult
    }

    private fun restoreBmiResult(savedInstanceState: Bundle?) {
        savedInstanceState?.getDouble(BMI_RESULT_KEY)?.let {
            result = it
        }
        binding.bmiResult = result
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun restoreUnitState(savedInstanceState: Bundle?) {
        savedInstanceState?.getParcelable<Unit>(UNIT_KEY)?.let {
            unitSwitch = it
        }
        binding.unit = unitSwitch
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.units_history_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_units) {
            unitSwitch = if (unitSwitch == Unit.KG_CM) Unit.LB_IN else Unit.KG_CM
            binding.unit = unitSwitch
            binding.executePendingBindings()
        }
        if (item.itemId == R.id.history) {
            val intent: Intent = Intent(this, HistoryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
        return true
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putDouble(BMI_RESULT_KEY, result)
            putParcelable(UNIT_KEY, unitSwitch)
            putParcelable(BMI_CATEGORY_KEY, bmiCategoryResult)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        result = savedInstanceState.getDouble(BMI_RESULT_KEY)
        binding.bmiTV.text = result.toString()
    }

    private fun addToHistory(bmiResult: Double, weight: Double, height: Double) {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.GERMAN)
        val bmiData =
            BmiData(
                bmiResult,
                weight,
                height,
                dateFormat.format(Calendar.getInstance().time),
                unitSwitch
            )
        bmiRepository.saveDate(bmiData)
    }

    fun count(view: View) {
        binding.apply {
            if (heightET.text.isBlank()) {
                heightET.error = getString(R.string.height_is_empty)
            }
            if (weightET.text.isBlank()) {
                weightET.error = getString(R.string.weight_is_empty)
            }
            if (!heightET.text.isBlank() && !weightET.text.isBlank()) {
                val bmiCalculator =
                    BmiCalculator()
                val weight = weightET.text.toString().toDouble()
                val height = heightET.text.toString().toDouble()
                result = bmiCalculator.count(
                    weightET.text.toString().toDouble(), heightET.text.toString()
                        .toDouble(), unitSwitch
                )

                bmiTV.text = result.toString()
                bmiCategoryResult = bmiCalculator.interpretResult(result)
                bmiCategory = bmiCategoryResult

                addToHistory(result, weight, height)
            }
        }
    }

}