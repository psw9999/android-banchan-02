package com.example.banchan.presentation.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.example.banchan.R
import com.example.banchan.databinding.ItemMenuFilterBinding

class MenuSpinner(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatSpinner(context, attributeSet) {

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
    }
}

class OptionAdapter(
    context: Context,
    private val optionValue: List<String>
) : ArrayAdapter<String>(context, R.layout.menu_filter) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun getCount() = optionValue.size
    override fun getItem(position: Int) = optionValue[position]
    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemMenuFilterBinding.inflate(layoutInflater, parent, false)
        binding.tvIssueType.text = "구현중"
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        convertView ?: ItemMenuFilterBinding.inflate(layoutInflater, parent, false).apply {

        }.root
}