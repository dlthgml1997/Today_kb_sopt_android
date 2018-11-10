package com.kb.challenge.app.today.today_android.network

import android.util.JsonToken
import com.kb.challenge.app.today.today_android.base.BaseModel
import com.kb.challenge.app.today.today_android.model.coin.CoinDetailResponse
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingData
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingResponse
import com.kb.challenge.app.today.today_android.model.login.LoginData
import com.kb.challenge.app.today.today_android.model.login.LoginResponse
import com.kb.challenge.app.today.today_android.model.record.FeelingData
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    // 통신에 사용할 함수를 정의
    //01. 로그인
    @POST("signin")
    fun login(
            @Body loginData : LoginData
    ) :Call<LoginResponse>

    //02. 감정 기록하기
    @POST("feeling")
    fun recordFeeling(
            @Header("authorization") token : String,
            @Body feelingData : FeelingData
    ) :Call<BaseModel>

    @POST("saving")
    fun savingMoney(
            @Header("authorization") token : String,
            @Body coinSavingData : CoinSavingData
    ) :Call<BaseModel>

    @GET("saving")
    fun getSavingList(
            @Header("authorization") token : String
    ) :Call<CoinSavingResponse>

    @GET("saving/goal")
    fun getSavingDetail(
            @Header("authorization") token : String
    ) : Call<CoinDetailResponse>
}