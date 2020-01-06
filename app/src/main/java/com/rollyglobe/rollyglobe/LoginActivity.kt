package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.rollyglobe.rollyglobe.response_model.NationCodeModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class LoginActivity : AppCompatActivity() {
    lateinit var compositeDisposable: CompositeDisposable
    val TAG = "LoginActivity_kgp"

    val nationCodeList = ArrayList<NationCodeModel>()
    val nationCodeStringList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        compositeDisposable = CompositeDisposable()


        compositeDisposable.add(
            RollyGlobeApi.getNationCodeInfoList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: List<NationCodeModel> ->

                    nationCodeList.addAll(response)
                    for(nation in nationCodeList){
                        nationCodeStringList.add(nation.toString())
                    }

                    initView()


                }, { error: Throwable ->
                    Log.d(TAG, error.localizedMessage)
                }))

    }
    fun initView(){

        //nation code spinner
        val nationCodeSpinnerAdapter = ArrayAdapter(this@LoginActivity, android.R.layout.simple_spinner_item,nationCodeStringList)
        nationCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nation_code_spinner.adapter = nationCodeSpinnerAdapter

        //gender spinner
        val genderSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item)
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gender_spinner.adapter = genderSpinnerAdapter


        //year spinner
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val gregorianCalendar = GregorianCalendar(timeZone)
        val year = gregorianCalendar.get(GregorianCalendar.YEAR)

        val yearList = ArrayList<Int>()
        for(y in year downTo year-100){
            yearList.add(y)

        }
        val yearSpinnerAdapter = ArrayAdapter(this@LoginActivity, android.R.layout.simple_spinner_item,yearList)
        yearSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        year_spinner.adapter = yearSpinnerAdapter
        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(praent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                updateLastDay()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        //month spinner
        val monthList = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val monthSpinnerAdapter = ArrayAdapter(this@LoginActivity, android.R.layout.simple_spinner_item, monthList)
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        month_spinner.adapter = monthSpinnerAdapter
        month_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(praent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                updateLastDay()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }



    }
    fun updateLastDay(){

        val year = year_spinner.selectedItem as Int
        val month = month_spinner.selectedItem as Int

        Log.i(TAG,"${year}")
        Log.i(TAG,"${month}")

        val lastDay = when(month){
            1-> 31
            2-> if ((year%4 ==0) && (year % 100 != 0) || (year % 400 == 0)) 28 else 29
            3->31
            4->30
            5->31
            6->30
            7->31
            8->31
            9->30
            10->31
            11->30
            12->31
            else -> 0
        }


        val days = Array(lastDay, {i -> i+1})
        val dayAdapter = ArrayAdapter(this@LoginActivity, android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        day_spinner.adapter = dayAdapter


    }
}
