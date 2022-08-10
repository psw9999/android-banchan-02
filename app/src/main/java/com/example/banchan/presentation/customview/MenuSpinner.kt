package com.example.banchan.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.example.banchan.R
import com.example.banchan.databinding.ItemMenuFilterBinding

class MenuSpinner(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatSpinner(context, attributeSet) {

    private lateinit var onFocusedListener: (Boolean) -> Unit
    private var isOpened = false

    fun setOnFocusedListener(onFocusedListener: (Boolean) -> Unit) {
        this.onFocusedListener = onFocusedListener
    }

    override fun performClick(): Boolean {
        if (::onFocusedListener.isInitialized) {
            isOpened = true
            onFocusedListener(isOpened)
        }
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (::onFocusedListener.isInitialized && isOpened && hasWindowFocus) {
            isOpened = false
            onFocusedListener(false)
        }
        super.onWindowFocusChanged(hasWindowFocus)
    }

    fun setOnItemClickListener(onItemClickListener: (Long) -> Unit) {
        this.onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemClickListener(id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemMenuFilterBinding.inflate(layoutInflater, parent, false)
        binding.tvIssueType.text = "안녕하세요"
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        convertView ?: ItemMenuFilterBinding.inflate(layoutInflater, parent, false).apply {
        }.root
}