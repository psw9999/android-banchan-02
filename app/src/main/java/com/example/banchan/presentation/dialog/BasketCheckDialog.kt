package com.example.banchan.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.banchan.R
import com.example.banchan.databinding.DialogBasketBinding
import com.example.banchan.presentation.basket.BasketFragment
import com.example.banchan.presentation.productdetail.ProductDetailFragment

class BasketCheckDialog : DialogFragment() {
    private var _binding: DialogBasketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBasketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initDialog()
    }

    private fun initDialog() {
        with(binding) {
            tvDialogBasketContinue.setOnClickListener {
                this@BasketCheckDialog.dismiss()
            }
            tvDialogBasketCheck.setOnClickListener {
                this@BasketCheckDialog.dismiss()
                requireActivity().supportFragmentManager.popBackStack(
                    BasketFragment.TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                requireActivity().supportFragmentManager.commit {
                    replace(R.id.layout_main_container, BasketFragment(), BasketFragment.TAG)
                    addToBackStack(BasketFragment.TAG)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "basket_check"
    }
}