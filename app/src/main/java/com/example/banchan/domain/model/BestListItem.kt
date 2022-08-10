package com.example.banchan.domain.model

sealed class BestListItem {
    data class BestHeader(
        val isSubTitleVisible: Boolean = true,
        val title: String = "한 번 주문하면 \n두 번 반하는 반찬들"
    ) : BestListItem()

    data class BestContent(val bestItem: BestModel) : BestListItem()

    object BestLoading : BestListItem()
    object BestError : BestListItem()
    object BestEmpty : BestListItem()
}