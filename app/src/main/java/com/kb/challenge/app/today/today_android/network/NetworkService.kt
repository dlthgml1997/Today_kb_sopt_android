package com.kb.challenge.app.today.today_android.network

import android.util.JsonToken
import com.kb.challenge.app.today.today_android.base.BaseModel
import com.kb.challenge.app.today.today_android.model.cheerup.CheerupMsgResponse
import com.kb.challenge.app.today.today_android.model.coin.CoinDetailResponse
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingData
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingResponse
import com.kb.challenge.app.today.today_android.model.login.LoginData
import com.kb.challenge.app.today.today_android.model.login.LoginResponse
import com.kb.challenge.app.today.today_android.model.record.FeelingData
import com.kb.challenge.app.today.today_android.model.setting.UserSettingData
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

    //03. 저금하기
    @POST("saving")
    fun savingMoney(
            @Header("authorization") token : String,
            @Body coinSavingData : CoinSavingData
    ) :Call<BaseModel>

    //04. 저금 내역 가져오기
    @GET("saving")
    fun getSavingList(
            @Header("authorization") token : String
    ) :Call<CoinSavingResponse>

    //05. 저금통 목표금액 목표 가져오기
    @GET("saving/goal")
    fun getSavingDetail(
            @Header("authorization") token : String
    ) : Call<CoinDetailResponse>

    //06. 인출하기
    @DELETE("saving")
    fun deleteDeposit(
            @Header("authorization") token : String
    ) :Call<BaseModel>

    //07. 위로의 말 가져오기
    @GET("comfort")
    fun getCheerupMsg(
            @Header("authorization") token : String
    ) :Call<CheerupMsgResponse>

    //08. 사용자 설정 저장
    @PUT("user")
    fun userSetting(
            @Header("authorization") token : String,
            @Body userSettingData : UserSettingData
    ) :Call<BaseModel>
}