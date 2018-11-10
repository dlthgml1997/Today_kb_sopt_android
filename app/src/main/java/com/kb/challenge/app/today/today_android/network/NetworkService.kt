package com.enterprise.hanjang.hanjang_android.network

import com.enterprise.hanjang.hanjang_android.base.BaseModel
import com.kb.challenge.app.today.today_android.model.login.LoginData
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by VictoryWoo
 */
interface NetworkService {
    // 통신에 사용할 함수를 정의
    //01. 로그인
    @POST("signup")
    fun signup(
            @Body loginData : LoginData
    ) :Call<BaseModel>

}