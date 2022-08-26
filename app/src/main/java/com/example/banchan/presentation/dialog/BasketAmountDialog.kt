package com.example.banchan.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.banchan.databinding.DialogAmountBinding
import com.example.banchan.domain.model.BasketModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketAmountDialog : DialogFragment() {

    private val viewModel: AmountDialogViewModel by viewModels()

    private var _binding: DialogAmountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAmountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initAmountDialog()
    }

    private fun initAmountDialog() {
        val basketModel = requireArguments().getParcelable<BasketModel>(BASKET_ITEM)
        basketModel?.let {
            if (viewModel.basketModel == null) {
                viewModel.setBasketModel(it)
                viewModel.setAmount(it.count)
            }
        }
        binding.npAmount.apply {
            minValue = 1
            maxValue = 99
            value = viewModel.amount.value
            setOnValueChangedListener { _, _, i2 ->
                viewModel.setAmount(i2)
            }
        }
        binding.tvCancel.setOnClickListener { this.dismiss() }
        binding.tvInput.setOnClickListener {
            viewModel.updateBasketItemAmount()
            this.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "basket_amount"
        const val BASKET_ITEM = "basket_item"
        fun newInstance(basketModel: BasketModel) = BasketAmountDialog().apply {
            arguments = Bundle().apply {
                putParcelable(BASKET_ITEM, basketModel)
            }
        }
    }
}