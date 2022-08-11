package com.example.banchan.presentation.custom

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.banchan.R
import com.example.banchan.databinding.ItemMenuFilterBinding
import com.example.banchan.presentation.home.maincook.Filter

class FilterAdapter(
    context: Context,
    private val selectedFilter: Filter,
    private val optionValue: Array<String>
) : ArrayAdapter<String>(context, R.layout.item_menu_filter) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun getCount() = optionValue.size
    override fun getItem(position: Int) = optionValue[position]
    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: layoutInflater.inflate(R.layout.item_menu_filter, parent, false)
        view.findViewById<TextView>(R.id.tv_filter_type).text = getItem(position)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        convertView ?: ItemMenuFilterBinding.inflate(layoutInflater, parent, false).apply {
            tvFilterType.text = getItem(position)
            ivCheckIcon.isVisible = position == selectedFilter.ordinal
        }.root
}