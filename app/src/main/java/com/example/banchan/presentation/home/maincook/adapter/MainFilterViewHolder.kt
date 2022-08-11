package com.example.banchan.presentation.home.maincook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.MenuFilterBinding
import com.example.banchan.presentation.custom.FilterAdapter
import com.example.banchan.presentation.home.maincook.Filter

class MainFilterViewHolder(private val binding: MenuFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        currentType: Type,
        onTypeChanged: (Type) -> Unit,
        currentFilter: Filter,
        onFilterChanged: (Filter) -> Unit
    ) {
        val grayColor = binding.root.resources.getColor(
            R.color.greyscale_default, null
        )
        val blackColor = binding.root.resources.getColor(
            R.color.black, null
        )

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

        binding.ivList.setOnClickListener {
            onTypeChanged(Type.Linear)
        }
        binding.ivGrid.setOnClickListener {
            onTypeChanged(Type.Grid)
        }

        when (currentType) {
            Type.Grid -> {
                binding.ivGrid.setColorFilter(blackColor)
                binding.ivList.setColorFilter(grayColor)
            }
            Type.Linear -> {
                binding.ivGrid.setColorFilter(grayColor)
                binding.ivList.setColorFilter(blackColor)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup) = MainFilterViewHolder(
            MenuFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}