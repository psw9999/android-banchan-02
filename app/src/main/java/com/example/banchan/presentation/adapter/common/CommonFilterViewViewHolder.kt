package com.example.banchan.presentation.adapter.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.TotalFilterBinding
import com.example.banchan.presentation.custom.FilterAdapter
import com.example.banchan.presentation.home.maincook.Filter

class CommonFilterViewViewHolder(private val binding: TotalFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(currentFilter: Filter, total: Int, onFilterChanged: (Filter) -> Unit) {
        binding.tvTotal.text = binding.root.resources.getString(R.string.total_format, total)

        binding.spinnerMain.adapter = FilterAdapter(
            binding.root.context,
            currentFilter,
            binding.root.resources.getStringArray(R.array.spinner_array)
        )
        binding.spinnerMain.setSelection(currentFilter.ordinal)
        binding.spinnerMain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (position) {
                    Filter.NORMAL.ordinal -> onFilterChanged(Filter.NORMAL)
                    Filter.PRICE_HIGH.ordinal -> onFilterChanged(Filter.PRICE_HIGH)
                    Filter.PRICE_LOW.ordinal -> onFilterChanged(Filter.PRICE_LOW)
                    Filter.SALE.ordinal -> onFilterChanged(Filter.SALE)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            CommonFilterViewViewHolder(
                TotalFilterBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}