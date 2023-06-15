package com.example.batikkita.api

import com.example.batikkita.response.BatikDetailResponse
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("getDetail")
    fun findBatikDetail(
        @Query("name") name: String
    ): Call<BatikDetailResponse>
}