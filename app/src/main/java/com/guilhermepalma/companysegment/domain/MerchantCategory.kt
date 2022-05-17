package com.guilhermepalma.companysegment.domain

import com.google.gson.annotations.SerializedName

data class MerchantCategory(

    @SerializedName("id")
    var id: String,

    @SerializedName("codigo")
    var code: Int,

    @SerializedName("agrupamento")
    var group: String
)
