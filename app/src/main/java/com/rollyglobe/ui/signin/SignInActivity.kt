package com.rollyglobe.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.rollyglobe.R
import com.rollyglobe.network.RollyGlobeApiClient
import com.rollyglobe.ui.signup.SignUpActivity
import com.rollyglobe.network.model.request_model.SignInOption
import com.rollyglobe.network.model.request_model.SignInRequest
import com.rollyglobe.network.model.request_model.SignInRequestModel
import com.rollyglobe.ui.MainActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_signin.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class SignInActivity : AppCompatActivity() {

    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()


    val focusListesner = View.OnFocusChangeListener{v, hasFocus ->

        when(v.id){
            signin_email_edit.id -> {
                signin_email_edit_underline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_blue
                ))
                signin_password_edit_underline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_gray
                ))

            }
            signin_password_edit.id->{
                signin_password_edit_underline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_blue
                ))
                signin_email_edit_underline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_gray
                ))
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

        disposable.add(restClient.signIn(signInRequestModel)
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
