package com.kb.challenge.app.today.today_android.model.login

import com.kb.challenge.app.today.today_android.base.BaseModel
import retrofit2.Call

/**
 * Created by shineeseo on 2018. 11. 10..
 */
data class LoginResponse (
        var token : String? = null
): BaseModel()