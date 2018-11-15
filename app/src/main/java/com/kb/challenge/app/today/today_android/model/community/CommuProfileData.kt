package com.kb.challenge.app.today.today_android.model.community

import java.sql.*;
/**
 * Created by shineeseo on 2018. 11. 15..
 */
data class CommuProfileData (
        var profile_url : String,
        var name : String,
        var id : String,
        var push_time : String,
        var countFollower : Int,
        var countFollowing :Int
)