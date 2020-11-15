package com.example.myapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityHistoryBinding
import com.example.myapplication.repository.BmiRepository
import com.example.myapplication.repository.BmiResultAdapter

class HistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistoryBinding
    lateinit var bmiRepository: BmiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        bmiRepository = BmiRepository(this)
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val readData = bmiRepository.readData()
        if (readData.isEmpty()) binding.emptyHistory.visibility = View.VISIBLE

        binding.historyRecyclerView.adapter =
            BmiResultAdapter(
                readData
            )
    }
}