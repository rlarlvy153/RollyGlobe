package com.rollyglobe.network

import com.google.gson.annotations.SerializedName
import okhttp3.Headers
import okhttp3.MultipartBody
import java.io.File

data class TestModel(
    @SerializedName("id")
    val id : String,

    @SerializedName("textarea")
    val text : String,

    @SerializedName("upload_files")
    val files : List<MultipartBody.Part>
)