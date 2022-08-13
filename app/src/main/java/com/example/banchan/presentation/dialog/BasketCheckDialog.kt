package com.example.banchan.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.banchan.databinding.DialogBasketBinding
import com.example.banchan.presentation.main.FragmentType
import com.example.banchan.presentation.main.MainViewModel

class BasketCheckDialog : DialogFragment() {

    companion object {
        const val TAG = "BasketCheckDialog"
    }

    private var _binding: DialogBasketBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

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
                mainViewModel.setCurrentFragment(FragmentType.Basket)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}