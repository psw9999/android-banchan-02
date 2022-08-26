package com.example.banchan.presentation.adapter.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.MenuFilterBinding
import com.example.banchan.presentation.custom.FilterAdapter
import com.example.banchan.presentation.home.Filter

class MainFilterViewHolder(private val binding: MenuFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        currentType: Type,
        onTypeChanged: (Type) -> Unit,
        currentFilter: Filter,
        onFilterChanged: (Filter) -> Unit
    ) {
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

        val resource = binding.root.resources
        when (currentType) {
            Type.Grid -> {
                binding.ivGrid.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resource,
                        R.drawable.grid_type_check,
                        null
                    )
                )
                binding.ivList.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resource,
                        R.drawable.linear_type_uncheck,
                        null
                    )
                )
            }
            Type.Linear -> {
                binding.ivGrid.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resource,
                        R.drawable.grid_type_uncheck,
                        null
                    )
                )
                binding.ivList.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resource,
                        R.drawable.linear_type_check,
                        null
                    )
                )
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