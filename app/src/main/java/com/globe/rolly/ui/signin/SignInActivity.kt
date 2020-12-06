package com.globe.rolly.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.globe.R
import com.globe.databinding.ActivitySigninBinding
import com.globe.databinding.BackArrowBinding
import com.globe.rolly.support.Utils
import com.globe.rolly.ui.MainActivity
import com.globe.rolly.ui.signup.SignUpActivity
import org.koin.android.ext.android.inject

class SignInActivity : AppCompatActivity() {

    private val signInViewModel: SignInViewModel by inject()

    val focusListesner = View.OnFocusChangeListener { v, hasFocus ->

        when (v.id) {
            binding.signinEmailEdit.id -> {
                binding.signinEmailEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_blue
                    )
                )
                binding.signinPasswordEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_gray
                    )
                )

            }
            binding.signinPasswordEdit.id -> {
                binding.signinPasswordEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_blue
                    )
                )
                binding.signinEmailEditUnderline.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.rg_gray
                    )
                )
            }
        }
    }

    private lateinit var binding: ActivitySigninBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()

        binding.signinEmailEdit.onFocusChangeListener = focusListesner
        binding.signinPasswordEdit.onFocusChangeListener = focusListesner

//        binding.backIcon.root.setOnClickListener {
//            finish()
//        }

        observeEvents()

    }

    private fun observeEvents() {
        signInViewModel.successSignIn.observe(this, Observer {
            if (it) {
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        signInViewModel.showErrorMsg.observe(this, Observer { msg ->
            if (msg.isNotEmpty() && msg.isNotBlank()) {
                Utils.showToast(msg)
            }
        })

    }

    fun onClickSignIn(v: View) {
        val emailAddress = binding.signinEmailEdit.text.toString()
        val pw = binding.signinPasswordEdit.text.toString()
        val auto = binding.keepLoginCheckbox.isChecked

        signInViewModel.signIn(emailAddress, pw, auto)
    }

    fun onClickSignUpText(v: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}
