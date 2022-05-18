package com.guilhermepalma.companysegment.data

import com.guilhermepalma.companysegment.domain.Segment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SegmentAPI {

    @GET("segmentos")
    fun getMatches(@Query("page") page: Int): Call<Segment>

}