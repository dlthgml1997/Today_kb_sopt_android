package com.kb.challenge.app.today.today_android.model.setting

import java.util.*
/**
 * Created by shineeseo on 2018. 11. 11..
 */
data class UserSettingData (
        var name : String,
        var profile_url : String?=null,
        var goal : String,
        var goal_money : Int,
        var push_time : Date
)