package com.example.banchan.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.banchan.databinding.BottomsheetBasketBinding
import com.example.banchan.presentation.main.BasketViewModel
import com.example.banchan.util.ext.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BasketBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BasketBottomSheet"
    }

    private val basketViewModel: BasketViewModel by activityViewModels()

    private var _binding: BottomsheetBasketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetBasketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet()
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                basketViewModel.isInsertSuccess.collectLatest { isInsertSuccess ->
                    if (isInsertSuccess) {
                        showDialog()
                        this@BasketBottomSheet.dismiss()
                    } else {
                        requireContext().toast("장바구니 저장 중 오류가 발생하였습니다.")
                    }
                }
            }
        }
    }

    private fun initBottomSheet() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = basketViewModel
        binding.tvBasketCancel.setOnClickListener {
            this.dismiss()
        }
        binding.btnBasketAdd.setOnClickListener {
            basketViewModel.insertSelectedBasketItem()
        }
    }

    private fun showDialog() {
        val targetDialog = parentFragmentManager.findFragmentByTag(BasketCheckDialog.TAG)
        if (targetDialog == null) {
            val checkDialog = BasketCheckDialog()
            checkDialog.isCancelable = false
            checkDialog.show(parentFragmentManager, BasketCheckDialog.TAG)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}