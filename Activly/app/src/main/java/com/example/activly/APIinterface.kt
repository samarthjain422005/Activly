package com.example.activly

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIinterface {
    @POST("api/v1/create")
    fun sendReq(@Body PostRequest: PostRequest?) : Call<PostRequest>
}
