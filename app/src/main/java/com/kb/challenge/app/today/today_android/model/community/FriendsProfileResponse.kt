package com.kb.challenge.app.today.today_android.model.community

import com.kb.challenge.app.today.today_android.base.BaseModel
import java.util.*

/**
 * Created by shineeseo on 2018. 11. 16..
 */
data class FriendsProfileResponse (
        var data : ArrayList<FriendsProfileData>
) :BaseModel()