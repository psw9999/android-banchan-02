package com.example.banchan

import com.example.banchan.data.response.ItemModel

val fakeData = listOf(
    ItemModel(
        id = 1,
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = "12,640원",
        discountPercent = "20%"
    ), ItemModel(
        id = 2,
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = "12,640원",
        discountPercent = "20%",
        isCartAdded = true
    ), ItemModel(
        id = 3,
        detailHash = "HBDEF",
        image = "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        title = "오리 주물럭_반조리",
        description = "감칠맛 나는 매콤한 양념",
        originPrice = "15,800",
        discountPrice = null,
        discountPercent = null
    )
)