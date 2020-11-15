package com.example.myapplication.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class BmiResultAdapter(private val bmiDataSet: List<BmiData>) :
    RecyclerView.Adapter<BmiResultAdapter.BmiViewHolder>() {
    class BmiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bmiResult: TextView = view.findViewById(R.id.bmiResult)
        val weight: TextView = view.findViewById(R.id.weight)
        val massUnit: TextView = view.findViewById(R.id.weight_unit)
        val height: TextView = view.findViewById(R.id.height)
        val heightUnit: TextView = view.findViewById(R.id.height_unit)
        val date: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BmiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bmi_result_adapter_item, parent, false)
        return BmiViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return bmiDataSet.size
    }

    override fun onBindViewHolder(holder: BmiViewHolder, position: Int) {
        holder.bmiResult.text = bmiDataSet[position].bmiResult.toString()
        holder.weight.text = bmiDataSet[position].weight.toString()
        holder.massUnit.setText(bmiDataSet[position].unit.weight)
        holder.height.text = bmiDataSet[position].height.toString()
        holder.date.text = bmiDataSet[position].date
        holder.heightUnit.setText(bmiDataSet[position].unit.height)
    }

}