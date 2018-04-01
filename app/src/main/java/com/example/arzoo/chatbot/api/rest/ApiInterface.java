package com.example.arzoo.chatbot.api.rest;

import com.example.arzoo.chatbot.api.AckResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by arzoo on 3/29/2018.
 */

public interface ApiInterface {
    @POST("data")
    @FormUrlEncoded
    Call<AckResponse> getResponse(@Field("name") String name, @Field("age") String age, @Field("symptoms") String symptoms);
}
