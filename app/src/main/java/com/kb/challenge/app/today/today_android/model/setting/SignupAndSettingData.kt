package com.kb.challenge.app.today.today_android.model.setting

import java.sql.Time

/**
 * Created by shineeseo on 2018. 11. 16..
 */
data class SignupAndSettingData (
        var id : String,
        var passwd : String,
        var name : String,
        var goal : String,
        var goal_money : Int,
        var push_time : Time
)