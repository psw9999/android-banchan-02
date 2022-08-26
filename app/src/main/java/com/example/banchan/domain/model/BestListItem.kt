package com.example.banchan.domain.model

import androidx.annotation.StringRes
import com.example.banchan.R

sealed class BestListItem {
    data class BestHeader(
        val isSubTitleVisible: Boolean = true,
        @StringRes val titleStrRes: Int = R.string.home_best_title
    ) : BestListItem()

    data class BestContent(val bestItem: BestModel) : BestListItem()

    object BestLoading : BestListItem()
    object BestError : BestListItem()
    object BestEmpty : BestListItem()
}