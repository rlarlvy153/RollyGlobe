package com.rollyglobe.rollyglobe

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.rollyglobe.rollyglobe.Model.request_model.SignUpOption
import com.rollyglobe.rollyglobe.Model.request_model.SignUpRequest
import com.rollyglobe.rollyglobe.Model.request_model.SignUpRequestModel
import com.rollyglobe.rollyglobe.Model.response_model.NationCodeResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signup.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class SignUpActivity : AppCompatActivity() {

    val nationCodeList = ArrayList<NationCodeResponseModel>()
    val nationCodeStringList = ArrayList<String>()

    private val disposable = CompositeDisposable()
    var genderIndex = 1
    var selectedYear = 1997
    var selectedMonth = 11
    var selectedDay = 27

    var restClient: RollyGlobeApiInterface =
        RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)

    lateinit var dayAdapter: ArrayAdapter<Int>


    val focusListesner = object : View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            setAllUnderlineToGray()
            val blue = ContextCompat.getColor(this@SignUpActivity, R.color.rg_blue)
            when (v?.id) {
                signup_email_edit.id -> {
                    signup_email_edit_underline.setBackgroundColor(blue)
                }
                signup_nickname_edit.id -> {
                    signup_nickname_edit_underline.setBackgroundColor(blue)
                }
                signup_password.id -> {
                    signup_password_edit_underline.setBackgroundColor(blue)
                }
                signup_password_again.id -> {
                    signup_password_again_edit_underline.setBackgroundColor(blue)
                }
            }
        }

        private fun setAllUnderlineToGray() {
            val gray = ContextCompat.getColor(this@SignUpActivity, R.color.rg_gray)

            signup_email_edit_underline.setBackgroundColor(gray)
            signup_nickname_edit_underline.setBackgroundColor(gray)
            signup_password_edit_underline.setBackgroundColor(gray)
            signup_password_again_edit_underline.setBackgroundColor(gray)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        signup_email_edit.onFocusChangeListener = focusListesner
        signup_nickname_edit.onFocusChangeListener = focusListesner
        signup_password.onFocusChangeListener = focusListesner
        signup_password_again.onFocusChangeListener = focusListesner

        val regularFont = ResourcesCompat.getFont(this,R.font.nanum_square_r)
        val boldFont = ResourcesCompat.getFont(this,R.font.nanum_square_b)

        val term2 = SpannableString(resources.getString(R.string.signup_terms2))
        val term3 = SpannableString(resources.getString(R.string.signup_terms3))

        val commaSpan  = SpannableString(", ")
        val term4 = SpannableString(resources.getString(R.string.signup_terms4))

        val term5 = SpannableString(resources.getString(R.string.signup_terms5))
        val blue = ContextCompat.getColor(this@SignUpActivity, R.color.rg_blue)
        val gray = ContextCompat.getColor(this@SignUpActivity, R.color.rg_gray)

        val clickableSpan3 = object : ClickableSpan(){
            override fun onClick(widget: View) {
                Timber.d("clicked 333")
            }
        }
        term3.setSpan(clickableSpan3, 0, term3.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term3.setSpan(ForegroundColorSpan(blue), 0,term3.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term3.setSpan(CustomTypefaceSpan("", boldFont!!),0,term3.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan4= object : ClickableSpan(){
            override fun onClick(widget: View) {
                Timber.d("clicked 444")
            }
        }
        term4.setSpan(clickableSpan4, 0, term4.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term4.setSpan(ForegroundColorSpan(blue), 0,term4.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        term4.setSpan(CustomTypefaceSpan("", boldFont!!),0,term4.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val result = TextUtils.concat(term2, term3, commaSpan,term4, term5)
        signup_terms2.setText(result)
        signup_terms2.movementMethod = LinkMovementMethod.getInstance()
        signup_terms2.highlightColor = Color.TRANSPARENT



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

        signup_date_text.text = getString(
            R.string.signup_date_text_string,
            selectedYear,
            selectedMonth,
            selectedDay
        )

        disposable.add(restClient.getNationCodeInfoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                for (code in result) {
                    nationCodeStringList.add(code.toString())
                }

                initView()
            }, {
                Timber.d("error")
            }

            )
        )

    }

    fun initView() {

        //nation code spinner
        val nationCodeSpinnerAdapter = ArrayAdapter(
            this@SignUpActivity,
            R.layout.nation_code_spinner_item,
            nationCodeStringList
        )
        nationCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nation_code_spinner.adapter = nationCodeSpinnerAdapter

        //gender spinner
//        val genderSpinnerAdapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.gender,
//            android.R.layout.simple_spinner_item
//        )
//        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        gender_spinner.adapter = genderSpinnerAdapter


        //year spinner
//        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
//        val gregorianCalendar = GregorianCalendar(timeZone)
//        val year = gregorianCalendar.get(GregorianCalendar.YEAR)
//
//        val yearList = ArrayList<Int>()
//        for (y in year downTo year - 100) {
//            yearList.add(y)
//
//        }
//        val yearSpinnerAdapter =
//            ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item, yearList)
//        yearSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        year_spinner.adapter = yearSpinnerAdapter
//        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                praent: AdapterView<*>?,
//                v: View?,
//                position: Int,
//                id: Long
//            ) {
//                updateLastDay()
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        }
//
//        //day spinner
//        dayAdapter = ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item, days)
//        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        day_spinner.adapter = dayAdapter
//
//        //month spinner
//        val monthList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
//        val monthSpinnerAdapter =
//            ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item, monthList)
//        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        month_spinner.adapter = monthSpinnerAdapter
//
//        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                praent: AdapterView<*>?,
//                v: View?,
//                position: Int,
//                id: Long
//            ) {
//                updateLastDay()
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        }


    }

//    fun updateLastDay() {
//
//        val year = year_spinner.selectedItem as Int
//        val month = month_spinner.selectedItem as Int
//
//        val lastDay = when (month) {
//            1 -> 31
//            2 -> if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) 29 else 28
//            3 -> 31
//            4 -> 30
//            5 -> 31
//            6 -> 30
//            7 -> 31
//            8 -> 31
//            9 -> 30
//            10 -> 31
//            11 -> 30
//            12 -> 31
//            else -> 0
//        }
//
//        while (days.size < lastDay) {
//            days.add(days.size + 1)
//        }
//        while (days.size > lastDay) {
//            days.removeAt(days.size - 1)
//        }
//
//        dayAdapter.notifyDataSetChanged()
//
//    }

    fun onClickGenderButton(v: View) {
        when (v.id) {
            R.id.gender_male_button -> {
                genderIndex = 1
                v.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.button_selected)
                )
                (v as Button).setTextColor(Color.WHITE)

                gender_female_button.setTextColor(Color.BLACK)
                gender_female_button.setBackground(resources.getDrawable(R.drawable.button_background))
            }
            R.id.gender_female_button -> {
                genderIndex = 2
                v.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.button_selected)
                )
                (v as Button).setTextColor(Color.WHITE)

                gender_male_button.setTextColor(Color.BLACK)
                gender_male_button.setBackground(resources.getDrawable(R.drawable.button_background))

            }
        }
    }

    fun onClicksignup(v: View) {
        val temp_email = signup_email_edit.text.toString()
        val temp_nickname = signup_nickname_edit.text.toString()
        val temp_password = signup_password.text.toString()
        val temp_password_again = signup_password_again.text.toString()
//        val temp_gender_pos = gender_spinner.selectedItemPosition // 1남 2여
        val temp_gender_pos = genderIndex
        val temp_gender = if (temp_gender_pos == 1) "male" else " female"
        val y = selectedYear.toString()
        val m = selectedMonth.toString()
        val d = selectedDay.toString()
        val temp_nation = nation_code_spinner.selectedItem.toString()
        val temp_trim_nation = temp_nation.substring(1, temp_nation.indexOf('('))
        val contact = phone_number_edit.text.toString()



        if (temp_password != temp_password_again) {

            Timber.d("비밀번호가 다름 $temp_password $temp_password_again")
            return
        }
        if (temp_gender_pos == 0) {
            Timber.e("성별 선택 안됨")
            return
        }
        Timber.i("메일 : $temp_email")
        Timber.i("별명 : $temp_nickname")
        Timber.i("비번 : $temp_password $temp_password_again")


        Timber.i("국가번호 : $temp_trim_nation")
        Timber.i("$temp_gender $y $m $d")


        val option = SignUpOption(
            temp_email,
            temp_nickname,
            temp_password,
            temp_trim_nation,
            contact,
            temp_gender,
            y,
            if (m.toInt() < 10) "0$m" else m,
            if (d.toInt() < 10) "0$d" else d
        )

        val signUpRequestModel = SignUpRequestModel()
        signUpRequestModel.request = SignUpRequest("SignUp", option)

        disposable.add(restClient.SignUp(signUpRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Timber.i(result.msg)
                Timber.i("${result.success}")
                finish()
            },
                {

                }
            )
        )

    }
}
