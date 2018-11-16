package com.kb.challenge.app.today.today_android.model.community

/**
 * Created by shineeseo on 2018. 11. 16..
 */
data class FriendsProfileData (
        var profile_img : String,
        var id : String,
        var name : String? = null,
        var good : Int? = null,
        var bad : Int? = null,
        var comment : String ?= null
)