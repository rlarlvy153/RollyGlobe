package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.rollyglobe.rollyglobe.request_model.SignInOption
import com.rollyglobe.rollyglobe.request_model.SignInRequest
import com.rollyglobe.rollyglobe.request_model.SignInRequestModel
import com.rollyglobe.rollyglobe.response_model.SignInModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    var restClient  = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
    val TAG = "SignUpActivity_kgp"
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        
    }
    fun onClickSignIn(v : View){
        val email_address = signin_email_edit.text.toString()
        val pw = signin_password_edit.text.toString()
        val auto = keep_login_checkbox.isChecked

        val option  = SignInOption("email",email_address, pw,auto)
        val signInRequest = SignInRequest("SignIn",option)
        val signInRequestModel = SignInRequestModel(signInRequest)

        disposable.add(restClient.SignIn(signInRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result->
                Log.i("kgp msg", result.toString())
                Log.i("kgp","${result?.user_info?.user_birthday}")
            },{

            })
        )
    }
}
