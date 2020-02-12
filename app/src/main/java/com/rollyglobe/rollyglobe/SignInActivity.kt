package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.rollyglobe.rollyglobe.request_model.SignInOption
import com.rollyglobe.rollyglobe.request_model.SignInRequest
import com.rollyglobe.rollyglobe.request_model.SignInRequestModel
import com.rollyglobe.rollyglobe.response_model.SignInModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    lateinit var restClient : RollyGlobeApiInterface
    val TAG = "SignInActivity_kgp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        restClient  = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)

    }
    fun onClickSignIn(v : View){
        val email_address = signin_email_edit.text.toString()
        val pw = signin_password_edit.text.toString()
        val auto = keep_login_checkbox.isChecked

        val option  = SignInOption("email",email_address, pw,auto)
        val signInRequest = SignInRequest("SignIn",option)
        val signInRequestModel = SignInRequestModel(signInRequest)

        val signInRequestCall = restClient.SignIn(signInRequestModel)

        signInRequestCall.enqueue(object : Callback<SignInModel> {
            override fun onFailure(call: Call<SignInModel>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<SignInModel>,response: Response<SignInModel>) {
                Log.i("kgp msg", response.message().toString())
                Log.i("kgp body", response.body().toString())
                Log.i("kgp raw", response.raw().toString())
                Log.i("kgp","${response.body()?.user_info?.user_birthday}")
            }
        })
    }
}
