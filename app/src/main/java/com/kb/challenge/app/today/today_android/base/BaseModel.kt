package com.enterprise.hanjang.hanjang_android.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by VictoryWoo
 */
open class BaseModel(
        @SerializedName("message")
        @Expose
        open var message : String?=null

)