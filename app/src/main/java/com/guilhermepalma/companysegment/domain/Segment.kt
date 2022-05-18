package com.guilhermepalma.companysegment.domain

import com.google.gson.annotations.SerializedName


data class Segment(

    @SerializedName("list")
    var segmentItems: List<Segment>,

    @SerializedName("id")
    var id: String,

    @SerializedName("descricao")
    var name: String,

    @SerializedName("mccs")
    var merchantCategory: List<MerchantCategory>
)
