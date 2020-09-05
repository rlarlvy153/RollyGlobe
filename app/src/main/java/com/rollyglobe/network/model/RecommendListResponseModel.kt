package com.rollyglobe.network.model

import com.google.gson.annotations.SerializedName

class RecommendListResponseModel (

    @SerializedName("msg")
    var msg:String,

    @SerializedName("success")
    var success:Boolean,

    @SerializedName("spot_list_total_cnt")
    var cnt:Int,

    @SerializedName("spot_list")
    var spotList:List<SpotModel>

)