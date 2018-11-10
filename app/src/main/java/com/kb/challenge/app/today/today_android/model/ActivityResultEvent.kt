package com.kb.challenge.app.today.today_android.model

import android.content.Intent

/**
 * Created by shineeseo on 2018. 11. 11..
 */
data class ActivityResultEvent (
        var requestCode : Int,
        var resultCode : Int,
        var data : Intent

)