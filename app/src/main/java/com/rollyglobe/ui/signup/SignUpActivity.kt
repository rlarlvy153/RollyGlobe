package com.rollyglobe.ui.signup

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.rollyglobe.R
import com.rollyglobe.support.Utils
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class SignUpActivity : AppCompatActivity() {

    var nationCodeStringList = ArrayList<String>()

    var genderIndex = 1
    var selectedYear = 1997
    var selectedMonth = 11
    var selectedDay = 27

    val signUpViewModel: SignUpViewModel by inject()

    val focusListesner = object : View.OnFocusChangeListener {
        fun getNewColor(hasFocus:Boolean) : Int{
            if(hasFocus){
                return ContextCompat.getColor(
                    this@SignUpActivity,
                    R.color.rg_blue
                )
            }
            else{
                return ContextCompat.getColor(
                    this@SignUpActivity,
                    R.color.rg_gray
                )
            }
        }
        override fun onFocusChange(v: View?, hasFocus: Boolean) {

            val newColor = getNewColor(hasFocus)

            when (v?.id) {
                signUpEmailEdit.id -> {
                    signUpEmailEditUnderline.setBackgroundColor(newColor)
                }
                signUpNicknameEdit.id -> {
                    signUpNicknameEditUnderline.setBackgroundColor(newColor)
                }
                signup_password.id -> {
                    signup_password_edit_underline.setBackgroundColor(newColor)
                }
                signup_password_again.id -> {
                    signup_password_again_edit_underline.setBackgroundColor(newColor)
                }
                signUpPhoneNumberEdit.id->{
                    signUpPhoneNumberEditUnderline.setBackgroundColor(newColor)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        addUnderlineFocusListener()

        initTermText()

        genderSelectListener()

        signup_date_text.text = getString(
            R.string.signup_date_text_string,
            selectedYear,
            selectedMonth,
            selectedDay
        )

        observeEvents()

        signUpViewModel.callNationCode()

    }

    private fun observeEvents() {

        signUpViewModel.nationCodeString.observe(this, Observer {
            nationCodeStringList = it

            initNationCodeSpinner()
        })

        signUpViewModel.signUpErrorMsg.observe(this, Observer {
            if (it.isNotBlank() && it.isNotEmpty()) {
                Utils.showToast(it)
            }
        })

        signUpViewModel.signUpResult.observe(this, Observer {
            if(it){
                Utils.showToast(Utils.getString(R.string.signup_success_signup))

                finish()
            }
        })
    }

    private fun genderSelectListener() {
        gender_birth_container.setOnClickListener {

            val dialog = DatePickerDialog(
                this,
                OnDateSetListener { datePicker, year, month, date ->
                    val msg =
                        String.format("%d 년 %d 월 %d 일", year, month + 1, date)
                    Timber.d(msg)

//                    signup_date_text.text="$selectedYear    / $selectedMonth    /  $selectedDay"
                    selectedYear = year
                    selectedMonth = month + 1
                    selectedDay = date
                    signup_date_text.text = getString(
                        R.string.signup_date_text_string,
                        selectedYear,
                        selectedMonth,
                        selectedDay
                    )
                },
                selectedYear,
                selectedMonth,
                selectedDay
            )

            dialog.datePicker.maxDate = Date().getTime() //입력한 날짜 이후로 클릭 안되게 옵션
            dialog.show()
        }
    }

    private fun addUnderlineFocusListener() {
        signUpEmailEdit.onFocusChangeListener = focusListesner
        signUpNicknameEdit.onFocusChangeListener = focusListesner
        signup_password.onFocusChangeListener = focusListesner
        signup_password_again.onFocusChangeListener = focusListesner
        signUpPhoneNumberEdit.onFocusChangeListener = focusListesner
    }

    private fun initTermText() {
        val boldFont = ResourcesCompat.getFont(
            this,
            R.font.nanum_square_b
        )

        val term2 = SpannableString(resources.getString(R.string.signup_terms2))
        val term3 = SpannableString(resources.getString(R.string.signup_terms3))

        val commaSpan = SpannableString(", ")
        val term4 = SpannableString(resources.getString(R.string.signup_terms4))

        val term5 = SpannableString(resources.getString(R.string.signup_terms5))
        val blue = ContextCompat.getColor(
            this@SignUpActivity,
            R.color.rg_blue
        )
        val gray = ContextCompat.getColor(
            this@SignUpActivity,
            R.color.rg_gray
        )

        val clickableSpan3 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Timber.d("clicked 333")
            }
        }
        term3.setSpan(clickableSpan3, 0, term3.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term3.setSpan(ForegroundColorSpan(blue), 0, term3.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term3.setSpan(
            StyleSpan(boldFont!!.style),
            0,
            term3.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val clickableSpan4 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Timber.d("clicked 444")
            }
        }
        term4.setSpan(clickableSpan4, 0, term4.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term4.setSpan(ForegroundColorSpan(blue), 0, term4.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term4.setSpan(
            StyleSpan(boldFont!!.style),
            0,
            term4.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val result = TextUtils.concat(term2, term3, commaSpan, term4, term5)
        signup_terms2.setText(result)
        signup_terms2.movementMethod = LinkMovementMethod.getInstance()
        signup_terms2.highlightColor = Color.TRANSPARENT
    }

    private fun initNationCodeSpinner() {

        //nation code spinner
        val nationCodeSpinnerAdapter = ArrayAdapter(
            this@SignUpActivity,
            R.layout.nation_code_spinner_item,
            nationCodeStringList
        )
        nationCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nation_code_spinner.adapter = nationCodeSpinnerAdapter
    }

    fun onClickGenderButton(v: View) {
        when (v.id) {
            R.id.gender_male_button -> {
                genderIndex = 1
                v.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.button_selected
                    )
                )
                (v as Button).setTextColor(Color.WHITE)

                gender_female_button.setTextColor(Color.BLACK)
                gender_female_button.setBackground(resources.getDrawable(R.drawable.button_background))
            }
            R.id.gender_female_button -> {
                genderIndex = 2
                v.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.button_selected
                    )
                )
                (v as Button).setTextColor(Color.WHITE)

                gender_male_button.setTextColor(Color.BLACK)
                gender_male_button.setBackground(resources.getDrawable(R.drawable.button_background))

            }
        }
    }

    fun onClicksignup(v: View) {
        val email = signUpEmailEdit.text.toString()
        val nickname = signUpNicknameEdit.text.toString()
        val password = signup_password.text.toString()
        val passwordAgain = signup_password_again.text.toString()
        val gender = if (genderIndex == 1) "male" else " female"
        val y = selectedYear.toString()
        val m = selectedMonth.toString()
        val d = selectedDay.toString()
        val nation = nation_code_spinner.selectedItem.toString()
        val trimmedNation = nation.substring(1, nation.indexOf('('))
        val contact = signUpPhoneNumberEdit.text.toString()

        if (email.isBlank() || email.isEmpty()) {
            Utils.showToast(Utils.getString(R.string.signup_empty_email))
            return
        }
        if (nickname.isBlank() || nickname.isEmpty()) {
            Utils.showToast(Utils.getString(R.string.signup_empty_nickname))
            return
        }

        if (password.isBlank() || password.isEmpty()) {
            Utils.showToast(Utils.getString(R.string.signup_empty_password))
            return
        }
        if (password != passwordAgain) {
            Utils.showToast(Utils.getString(R.string.signup_different_password))
            return
        }
        if(contact.isBlank() || contact.isEmpty()){
            Utils.showToast(Utils.getString(R.string.signup_empty_contact))
            return
        }

        signUpViewModel.signUp(
            email,
            nickname,
            password,
            trimmedNation,
            contact,
            gender, y, m, d
        )
    }
}
