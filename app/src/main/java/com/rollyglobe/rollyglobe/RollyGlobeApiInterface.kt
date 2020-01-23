package com.rollyglobe.rollyglobe

import com.google.gson.JsonObject
import com.rollyglobe.rollyglobe.response_model.NationCodeModel
import com.rollyglobe.rollyglobe.response_model.SignUpModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.POST

interface RollyGlobeApiInterface {

        @GET("/codeNumbers/nationCode.json")
        fun getNationCodeInfoList(): Call<List<NationCodeModel>>

        @POST("/ajax/user.php")
        fun SignUp(@Body param : HashMap<String, Any> ):Call<SignUpModel>

        @POST("/ajax/user.php")
        fun SignUp2(@Body param : RequestBody):Call<SignUpModel>
}
