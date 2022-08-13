package com.example.banchan.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.banchan.databinding.BottomsheetBasketBinding
import com.example.banchan.domain.model.BasketModel
import com.example.banchan.presentation.main.BasketViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
    }

    private fun initBottomSheet() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = basketViewModel
        binding.tvBasketCancel.setOnClickListener {
            this.dismiss()
        }
        binding.btnBasketAdd.setOnClickListener {
            //TODO : 장바구니 DB에 저장하는 로직
            with(basketViewModel) {
                addBasketList(
                    BasketModel(
                        count = selectedBasketCount.value!!,
                        image = selectedBasketItem.value!!.image,
                        price = selectedBasketItem.value!!.originPrice,
                        name = selectedBasketItem.value!!.title,
                        detailHash = selectedBasketItem.value!!.detailHash
                    )
                )
            }
            this.dismiss()
            showDialog()
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