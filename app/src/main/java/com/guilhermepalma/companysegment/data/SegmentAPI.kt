package com.guilhermepalma.companysegment.data

import com.guilhermepalma.companysegment.domain.Segment
import retrofit2.Call
import retrofit2.http.GET

interface SegmentAPI {

    @GET("matches.json")
    fun getMatches(): Call<List<Segment>>

}