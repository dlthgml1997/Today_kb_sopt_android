package com.kb.challenge.app.today.today_android.network

import com.kb.challenge.app.today.today_android.base.BaseModel
import com.kb.challenge.app.today.today_android.model.cheerup.CheerupMsgResponse
import com.kb.challenge.app.today.today_android.model.coin.CoinDetailResponse
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingData
import com.kb.challenge.app.today.today_android.model.coin.CoinSavingResponse
import com.kb.challenge.app.today.today_android.model.community.*
import com.kb.challenge.app.today.today_android.model.login.*
import com.kb.challenge.app.today.today_android.model.push.PushTimeData
import com.kb.challenge.app.today.today_android.model.record.FeelingData
import com.kb.challenge.app.today.today_android.model.record.FeelingDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.sql.Time

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
    @Multipart
    @PUT("user")
    fun userSetting(
            @Header("authorization") token : String,
            @Part("name") name :RequestBody,
            @Part profile_url : MultipartBody.Part,
            @Part("goal") goal:RequestBody,
            @Part("goal_money") goal_money:Int,
            @Part("push_time") push_time: Time
            ) :Call<BaseModel>

//    //09.회원가입
//    @POST("signup")
//    fun signup(
//            @Body signupAndSettingData : SignupAndSettingData
//    ) :Call<BaseModel>

    //09.회원가입
    @POST("signup")
    fun signup(
            @Body signupData: SignupData
    ) :Call<BaseModel>

    //10.아이디 중복체크
    @POST("signup/check")
    fun signupCheckId(
            @Body id : SignupCheckData
    ) :Call<BaseModel>

    @POST("follow/{id}")
    fun followUser(
            @Header("authorization") token : String,
            @Path("id") id : String
    ) :Call<BaseModel>

    @GET("follower")
    fun getFollowerList(
            @Header("authorization") token : String
    ) :Call<FollowListResponse>

    @GET("following")
    fun getFollowingList(
            @Header("authorization") token : String
    ) :Call<FollowingListResponse>

    @GET("user")
    fun getUserProfile(
            @Header("authorization") token : String
    ):Call<CommuProfileResponse>

    @DELETE("follow/{id}")
    fun cancelFollow(
            @Header("authorization") token : String,
            @Path("id") id : String
    ) :Call<BaseModel>

    @GET("follow/{date}")
    fun getFollowingsFeeling(
            @Header("authorization") token : String,
            @Path("date") today : String
    ) :Call<FriendsProfileResponse>

    @GET("today/{date}")
    fun getTodayFeeling(
            @Header("authorization") token : String,
            @Path("date") today : String
    ):Call<FeelingDataResponse>

    @GET("search")
    fun searchUser(
            @Query("id") id : String
    ) :Call<SearchUserResponse>

    @POST("box")
    fun sendCheerupMsg(
            @Header("authorization") token : String,
            @Body signupData : SignupData
    )

    @GET("user/name")
    fun getUserName(
            @Header("authorization") token : String
    ) :Call<UserNameData>

    @GET("box")
    fun getEmotionBoxList(
            @Header("authorization") token : String,
            @Query("today") today : String
    ) :Call<EmotionBoxResponse>

    @POST("box")
    fun sendEmotionBox(
            @Header("authorization") token : String,
            @Body emotionData : SendEmotionData
    ):Call<BaseModel>

    @GET("user/time")
    fun getPushTime(
            @Header("authorization") token : String
    ) :Call<PushTimeData>
}