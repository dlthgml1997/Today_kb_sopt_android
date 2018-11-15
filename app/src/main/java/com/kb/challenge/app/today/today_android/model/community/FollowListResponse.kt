package com.kb.challenge.app.today.today_android.model.community

import com.kb.challenge.app.today.today_android.base.BaseModel
import java.util.*

/**
 * Created by shineeseo on 2018. 11. 15..
 */
data class FollowListResponse (
        var data : ArrayList<FollowerData>
) : BaseModel()