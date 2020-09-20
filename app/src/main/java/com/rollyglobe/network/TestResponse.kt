package com.rollyglobe.network

import com.google.gson.annotations.SerializedName

data class FileInfo(
    @SerializedName("file_path")
    val path : String,

    @SerializedName("file_name")
    val name : String
)

data class TestResponse(
    @SerializedName("id")
    val id:String,

    @SerializedName("textarea")
    val text:String,

    @SerializedName("files")
    val fileInfo : List<FileInfo>
)