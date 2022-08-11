package com.example.banchan.presentation.home.maincook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.MenuFilterBinding
import com.example.banchan.presentation.custom.OptionAdapter

class MainFilterViewHolder(private val binding: MenuFilterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(currentFilter: Type, onClick: (Type) -> Unit) {
        val grayColor = binding.root.resources.getColor(
            R.color.greyscale_default, null
        )
        val blackColor = binding.root.resources.getColor(
            R.color.black, null
        )
        binding.spinnerMain.adapter = OptionAdapter(binding.root.context, listOf("안녕", "안녕"))
        binding.ivList.setOnClickListener {
            onClick(Type.Linear)
        }
        binding.ivGrid.setOnClickListener {
            onClick(Type.Grid)
        }

        when (currentFilter) {
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