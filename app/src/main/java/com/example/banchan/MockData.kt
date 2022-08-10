package com.example.banchan

import com.example.banchan.domain.model.BestListItem
import com.example.banchan.domain.model.BestModel
import com.example.banchan.domain.model.ItemModel

var fakeData: MutableList<ItemModel> = mutableListOf(
    ItemModel(
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = "12,640원",
        discountPercent = "20%"
    ),
    ItemModel(
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = "12,640원",
        discountPercent = "20%",
        isCartAdded = true
    ),
    ItemModel(
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = null,
        discountPercent = null
    ),
    ItemModel(
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = null,
        discountPercent = null
    ),
    ItemModel(
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = null,
        discountPercent = null
    ),
    ItemModel(
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = null,
        discountPercent = null
    )
)

val fakeItem = listOf(
    ItemModel(
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = null,
        discountPercent = null
    )
)

val fakeBestItem: List<BestListItem> = listOf(
    BestListItem.BestHeader,
    BestListItem.BestContent(
        BestModel(
            title = "풍성한 고기반찬",
            items = fakeItem
        )
    ),
    BestListItem.BestContent(
        BestModel(
            title = "편리한 반찬세트",
            items = fakeItem
        )
    ),
    BestListItem.BestContent(
        BestModel(
            title = "우리 아빠 영양 간식",
            items = fakeItem
        )
    ),
    BestListItem.BestContent(
        BestModel(
            title = "우리 아이 술안주",
            items = fakeItem
        )
    ),
    BestListItem.BestContent(
        BestModel(
            title = "배고프다",
            items = fakeItem
        )
    ),
    BestListItem.BestContent(
        BestModel(
            title = "맛좋은 고기",
            items = fakeItem
        )
    )
)
