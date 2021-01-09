package com.globe.rolly.ui.signup

import android.app.DatePickerDialog
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.globe.R
import com.globe.databinding.ActivitySignupBinding
import com.globe.rolly.support.Utils
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class SignUpActivity : AppCompatActivity() {

    private var nationCodeStringList = ArrayList<String>()

    private var genderIndex = 1
    private var selectedYear = 1997
    private var selectedMonth = 11
    private var selectedDay = 27

    private val signUpViewModel: SignUpViewModel by inject()

    private val focusListener = object : View.OnFocusChangeListener {
        fun getNewColor(hasFocus: Boolean): Int {
            return if (hasFocus) {
                ContextCompat.getColor(
                    this@SignUpActivity,
                    R.color.rg_blue
                )
            } else {
                ContextCompat.getColor(
                    this@SignUpActivity,
                    R.color.rg_gray
                )
            }
        }

        override fun onFocusChange(v: View?, hasFocus: Boolean) {

            val newColor = getNewColor(hasFocus)

            when (v?.id) {
                binding.signUpEmailEdit.id -> {
                    binding.signUpEmailEditUnderline.setBackgroundColor(newColor)
                }
                binding.signUpNicknameEdit.id -> {
                    binding.signUpNicknameEditUnderline.setBackgroundColor(newColor)
                }
                binding.signupPassword.id -> {
                    binding.signupPasswordEditUnderline.setBackgroundColor(newColor)
                }
                binding.signupPasswordAgain.id -> {
                    binding.signupPasswordAgainEditUnderline.setBackgroundColor(newColor)
                }
                binding.signUpPhoneNumberEdit.id -> {
                    binding.signUpPhoneNumberEditUnderline.setBackgroundColor(newColor)
                }
            }
        }
    }

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.backIcon.backIconImageView.setOnClickListener {
            finish()
        }

        addUnderlineFocusListener()

        initTermText()

        genderSelectListener()

        binding.signupDateText.text = getString(
            R.string.signup_date_text_string,
            selectedYear,
            selectedMonth,
            selectedDay
        )

        observeEvents()

        signUpViewModel.callNationCode()

    }

    private fun observeEvents() {

        signUpViewModel.nationCodeString.observe(this, {
            nationCodeStringList = it

            initNationCodeSpinner()
        })

        signUpViewModel.signUpErrorMsg.observe(this, {
            if (it.isNotBlank() && it.isNotEmpty()) {
                Utils.showToast(it)
            }
        })

        signUpViewModel.signUpResult.observe(this, {
            if (it) {
                Utils.showToast(Utils.getString(R.string.signup_success_signup))

                finish()
            }
        })
    }

    private fun genderSelectListener() {
        binding.genderBirthContainer.setOnClickListener {

            val dialog = DatePickerDialog(
                this,
                { _, year, month, date ->
                    val msg =
                        String.format("%d 년 %d 월 %d 일", year, month + 1, date)
                    Timber.d(msg)

//                    signup_date_text.text="$selectedYear    / $selectedMonth    /  $selectedDay"
                    selectedYear = year
                    selectedMonth = month + 1
                    selectedDay = date
                    binding.signupDateText.text = getString(
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

            dialog.datePicker.maxDate = Date().time //입력한 날짜 이후로 클릭 안되게 옵션
            dialog.show()
        }
    }

    private fun addUnderlineFocusListener() {
        binding.signUpEmailEdit.onFocusChangeListener = focusListener
        binding.signUpNicknameEdit.onFocusChangeListener = focusListener
        binding.signupPassword.onFocusChangeListener = focusListener
        binding.signupPasswordAgain.onFocusChangeListener = focusListener
        binding.signUpPhoneNumberEdit.onFocusChangeListener = focusListener
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
            StyleSpan(boldFont.style),
            0,
            term4.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val result = TextUtils.concat(term2, term3, commaSpan, term4, term5)
        binding.signupTerms2.text = result
        binding.signupTerms2.movementMethod = LinkMovementMethod.getInstance()
        binding.signupTerms2.highlightColor = Color.TRANSPARENT
    }

    private fun initNationCodeSpinner() {

        //nation code spinner
        val nationCodeSpinnerAdapter = ArrayAdapter(
            this@SignUpActivity,
            R.layout.nation_code_spinner_item,
            nationCodeStringList
        )
        nationCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.nationCodeSpinner.adapter = nationCodeSpinnerAdapter
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

                binding.genderFemaleButton.setTextColor(Color.BLACK)
                binding.genderFemaleButton.background = ContextCompat.getDrawable(this, R.drawable.button_background)
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

                binding.genderMaleButton.setTextColor(Color.BLACK)
                binding.genderMaleButton.background = ContextCompat.getDrawable(this, R.drawable.button_background)

            }
        }
    }

    fun onClicksignup(v: View) {
        val email = binding.signUpEmailEdit.text.toString()
        val nickname = binding.signUpNicknameEdit.text.toString()
        val password = binding.signupPassword.text.toString()
        val passwordAgain = binding.signupPasswordAgain.text.toString()
        val gender = if (genderIndex == 1) "male" else " female"
        val y = selectedYear.toString()
        val m = selectedMonth.toString()
        val d = selectedDay.toString()
        val nation = binding.nationCodeSpinner.selectedItem.toString()
        val trimmedNation = nation.substring(1, nation.indexOf('('))
        val contact = binding.signUpPhoneNumberEdit.text.toString()

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
        if (contact.isBlank() || contact.isEmpty()) {
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
