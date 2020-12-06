package com.globe.rolly.ui.my_page.edit_info_activities

import android.content.Intent
import android.icu.util.GregorianCalendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.globe.R
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.edit_user_info.birthday.EditUserBirthdayRequestModel
import com.globe.rolly.ui.my_page.ProfileEditActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_page_edit_birthday.*
import org.koin.android.ext.android.inject


class MyPageEditBirthdayActivity : AppCompatActivity() {
    val days = MutableList(28, { i -> i + 1 })
    val restClient: RollyGlobeApiClient by inject()

    private val disposable = CompositeDisposable()
    lateinit var dayAdapter: ArrayAdapter<Int>

    var userBirthYear = -1
    var userBirthMonth = -1
    var userBirthDay = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_edit_birthday)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_birthday)


        //year spinner
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val gregorianCalendar = GregorianCalendar(timeZone)
        val year = gregorianCalendar.get(GregorianCalendar.YEAR)

        val yearList = ArrayList<Int>()
        for (y in year downTo year - 100) {
            yearList.add(y)
        }

        val yearSpinnerAdapter =
            ArrayAdapter(
                this@MyPageEditBirthdayActivity,
                android.R.layout.simple_spinner_item,
                yearList
            )
        yearSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edit_year_spinner.adapter = yearSpinnerAdapter
        edit_year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                praent: AdapterView<*>?,
                v: View?,
                position: Int,
                id: Long
            ) {
                updateLastDay()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        //day spinner
        dayAdapter = ArrayAdapter(
            this@MyPageEditBirthdayActivity,
            android.R.layout.simple_spinner_item,
            days
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edit_day_spinner.adapter = dayAdapter

        //month spinner
        val monthList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        val monthSpinnerAdapter =
            ArrayAdapter(
                this@MyPageEditBirthdayActivity,
                android.R.layout.simple_spinner_item,
                monthList
            )
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edit_month_spinner.adapter = monthSpinnerAdapter

        edit_month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                praent: AdapterView<*>?,
                v: View?,
                position: Int,
                id: Long
            ) {
                updateLastDay()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        val userBirthdayString = intent.getStringExtra(ProfileEditActivity.EDIT_BIRTHDAY)
        userBirthYear = userBirthdayString.substring(0, 4).toInt()

        userBirthMonth = userBirthdayString.substring(4, 6).toInt()
        userBirthDay = userBirthdayString.substring(6, 8).toInt()

        edit_year_spinner.setSelection(yearList.indexOf(userBirthYear))
        edit_month_spinner.setSelection(monthList.indexOf(userBirthMonth))
        edit_day_spinner.setSelection(monthList.indexOf(userBirthDay))

    }

    fun updateLastDay() {

        val year = edit_year_spinner.selectedItem as Int
        val month = edit_month_spinner.selectedItem as Int

        val lastDay = when (month) {
            1 -> 31
            2 -> if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) 29 else 28
            3 -> 31
            4 -> 30
            5 -> 31
            6 -> 30
            7 -> 31
            8 -> 31
            9 -> 30
            10 -> 31
            11 -> 30
            12 -> 31
            else -> 0
        }

        while (days.size < lastDay) {
            days.add(days.size + 1)
        }
        while (days.size > lastDay) {
            days.removeAt(days.size - 1)
        }

        dayAdapter.notifyDataSetChanged()

    }

    fun onClickApplyBtn(v: View) {
        val y = edit_year_spinner.selectedItem.toString()
        var m = edit_month_spinner.selectedItem.toString()
        if (m.length == 1)
            m = "0$m"
        var d = edit_day_spinner.selectedItem.toString()
        if (d.length == 1) {
            d = "0$d"
        }

        val requestModel = EditUserBirthdayRequestModel(y, m, d)

        disposable.add(
            restClient.editUserBirthday(requestModel)
                .subscribe({ result ->
                    if (result.success) {
                        val intent = Intent()
                        intent.putExtra(ProfileEditActivity.EDIT_BIRTHDAY, "$y$m$d")
                        setResult(ProfileEditActivity.RESULT_CODE_BIRTHDAY, intent)
                        finish()
                    } else {
                        val intent = Intent()

                        setResult(ProfileEditActivity.RESULT_CODE_FAIL, intent)
                        finish()
                    }


                }, {

                })
        )
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
