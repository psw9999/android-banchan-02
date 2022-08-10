package com.example.banchan.domain.model

sealed class BestListItem {
    object BestHeader : BestListItem()
    class BestContent(val bestItem: BestModel) : BestListItem()
}