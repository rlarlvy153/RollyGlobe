package com.globe.rolly.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.globe.R
import com.globe.rolly.ui.signup.SignUpActivity
import com.globe.rolly.support.Utils
import com.globe.rolly.ui.MainActivity
import kotlinx.android.synthetic.main.activity_signin.*
import org.koin.android.ext.android.inject

class SignInActivity : AppCompatActivity() {

    private val signInViewModel: SignInViewModel by inject()

    val focusListesner = View.OnFocusChangeListener { v, hasFocus ->

        when (v.id) {
            signinEmailEdit.id -> {
                signinEmailEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_blue
                    )
                )
                signinPasswordEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_gray
                    )
                )

            }
            signinPasswordEdit.id -> {
                signinPasswordEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_blue
                    )
                )
                signinEmailEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_gray
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        signinEmailEdit.onFocusChangeListener = focusListesner
        signinPasswordEdit.onFocusChangeListener = focusListesner

        backIcon.setOnClickListener {
            finish()
         }

        observeEvents()

    }

    private fun observeEvents() {
        signInViewModel.successSignIn.observe(this, Observer {
            if(it){
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        signInViewModel.showErrorMsg.observe(this, Observer{msg ->
            if(msg.isNotEmpty() && msg.isNotBlank()){
                Utils.showToast(msg)
            }
        })

    }

    fun onClickSignIn(v: View) {
        val emailAddress = signinEmailEdit.text.toString()
        val pw = signinPasswordEdit.text.toString()
        val auto = keepLoginCheckbox.isChecked

        signInViewModel.signIn(emailAddress, pw, auto)
    }

    fun onClickSignUpText(v: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}
