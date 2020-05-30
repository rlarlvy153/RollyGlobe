package com.rollyglobe.rollyglobe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.rollyglobe.rollyglobe.Model.request_model.SignInOption
import com.rollyglobe.rollyglobe.Model.request_model.SignInRequest
import com.rollyglobe.rollyglobe.Model.request_model.SignInRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signin.*
import timber.log.Timber
import kotlin.math.absoluteValue

class SignInActivity : AppCompatActivity() {

    var restClient  = RestClient.restClient
    private val disposable = CompositeDisposable()
    val focusListesner = View.OnFocusChangeListener{v, hasFocus ->
        when(v.id){
            signin_email_edit.id -> {
                signin_email_edit_underline.setBackgroundColor(ContextCompat.getColor(this,R.color.rg_blue))
                signin_password_edit_underline.setBackgroundColor(ContextCompat.getColor(this,R.color.rg_gray))
            }
            signin_password_edit.id->{
                signin_password_edit_underline.setBackgroundColor(ContextCompat.getColor(this,R.color.rg_blue))
                signin_email_edit_underline.setBackgroundColor(ContextCompat.getColor(this,R.color.rg_gray))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        signin_email_edit.onFocusChangeListener = focusListesner
        signin_password_edit.onFocusChangeListener = focusListesner

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
                if(!result.success){
                    Toast.makeText(this@SignInActivity,result.msg,Toast.LENGTH_SHORT).show()
                    return@subscribe
                }

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
