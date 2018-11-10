package com.kb.challenge.app.today.today_android.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseModel(
        @SerializedName("message")
        @Expose
        open var message : String?=null

)