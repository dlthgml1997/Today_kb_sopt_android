package com.kb.challenge.app.today.today_android.model.push

import com.kb.challenge.app.today.today_android.base.BaseModel

/**
 * Created by shineeseo on 2018. 11. 18..
 */
data class PushTimeData (
       var hour : String,
       var minute : String,
       var second : String
) :BaseModel()