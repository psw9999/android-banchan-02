package com.example.banchan.presentation.home.best

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.banchan.R
import com.example.banchan.databinding.FragmentBestBinding
import com.example.banchan.domain.model.BestListItem
import com.example.banchan.domain.model.BestModel
import com.example.banchan.presentation.adapter.best.BestListAdapter
import com.example.banchan.presentation.home.HomeTabFragment
import com.example.banchan.util.dimen.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BestFragment : HomeTabFragment<FragmentBestBinding>(R.layout.fragment_best) {

    private val bestViewModel: BestViewModel by viewModels()
    private val bestListAdapter by lazy { BestListAdapter(basketIconClickListener, detailClickListener) }

    override fun onStart() {
        super.onStart()
        bestViewModel.getBestDishes()
    }

    override fun initViews() {
        initRecyclerView()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    bestViewModel.bestDishes.combine(basketViewModel.basketList) { bestDishes, basketList ->
                        bestDishes.map { dish ->
                            if (dish is BestListItem.BestContent) {
                                dish.copy(
                                    bestItem = BestModel(
                                        title = dish.bestItem.title,
                                        items = dish.bestItem.items.map { model ->
                                            model.copy(
                                                isCartAdded = model.detailHash in basketList.map { it.detailHash }
                                            )
                                        }
                                    )
                                )
                            }
                            else {
                                dish
                            }
                        }
                    }.collectLatest {
                        bestListAdapter.submitList(it)
                    }
            }
        }
    }

    private fun initRecyclerView(){
        binding.rvBestList.adapter = bestListAdapter
        binding.rvBestList.addItemDecoration(object: RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == 0) outRect.bottom = dpToPx(requireContext(), 24)
                else outRect.bottom = dpToPx(requireContext(), 40)
            }
        })
    }

}
