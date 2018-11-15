package com.kb.challenge.app.today.today_android.model.cheerup

import com.kb.challenge.app.today.today_android.base.BaseModel
import java.util.*
/**
 * Created by shineeseo on 2018. 11. 11..
 */
data class CheerupMsgResponse (
        var comfortImg : ArrayList<ComfortImgData>,
        var data : ArrayList<CheerupMsgData>
) :BaseModel()