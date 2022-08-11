package com.example.banchan.presentation.home.maincook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.MenuFilterBinding
import com.example.banchan.presentation.custom.OptionAdapter
import com.example.banchan.presentation.home.maincook.Filter

class MainFilterViewHolder(private val binding: MenuFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(currentType: Type, onTypeChanged: (Type) -> Unit, currentFilter: Filter, onFilterChanged: (Filter) -> Unit) {
        val grayColor = binding.root.resources.getColor(
            R.color.greyscale_default, null
        )
        val blackColor = binding.root.resources.getColor(
            R.color.black, null
        )
        binding.spinnerMain.adapter = OptionAdapter(
            binding.root.context,
            binding.root.resources.getStringArray(R.array.spinner_array)
        )
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