package com.rollyglobe.rollyglobe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rollyglobe.rollyglobe.Model.request_model.SignInOption
import com.rollyglobe.rollyglobe.Model.request_model.SignInRequest
import com.rollyglobe.rollyglobe.Model.request_model.SignInRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber

class SignInActivity : AppCompatActivity() {

    var restClient  = RestClient.restClient
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
        supportActionBar?.hide()


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
                Timber.d(result.toString())
                Timber.d("${result?.user_info?.user_birthday}")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },{

            })
        )
    }
    fun onClickSignUpText(v : View){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}
