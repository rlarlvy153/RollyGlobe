package com.rollyglobe.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.rollyglobe.R
import com.rollyglobe.network.RollyGlobeApiClient
import com.rollyglobe.ui.signup.SignUpActivity
import com.rollyglobe.network.model.user.signin.SignInOption
import com.rollyglobe.network.model.user.signin.SignInRequest
import com.rollyglobe.network.model.user.signin.SignInRequestModel
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
            signinEmailEdit.id -> {
                signinEmailEditUnderline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_blue
                ))
                signinPasswordEditUnderline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_gray
                ))

            }
            signinPasswordEdit.id->{
                signinPasswordEditUnderline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_blue
                ))
                signinEmailEditUnderline.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.rg_gray
                ))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        signinEmailEdit.onFocusChangeListener = focusListesner
        signinPasswordEdit.onFocusChangeListener = focusListesner

    }
    fun onClickSignIn(v : View){
        val email_address = signinEmailEdit.text.toString()
        val pw = signinPasswordEdit.text.toString()
        val auto = keepLoginCheckbox.isChecked

        val option  = SignInOption("email", email_address, pw, auto)
        val signInRequest = SignInRequest("SignIn", option)
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
