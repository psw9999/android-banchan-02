package com.example.banchan.data.response.best

import com.example.banchan.data.response.BestModel

sealed class BestListItem {
    object BestHeader : BestListItem()
    class BestContent(val bestItem: BestModel) : BestListItem()
}