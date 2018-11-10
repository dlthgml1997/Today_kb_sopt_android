package com.kb.challenge.app.today.today_android.model.coin

import com.kb.challenge.app.today.today_android.base.BaseModel
import java.util.*

/**
 * Created by shineeseo on 2018. 11. 11..
 */
data class CoinDetailResponse (
        var data : ArrayList<CoinDetailData>
) :BaseModel()