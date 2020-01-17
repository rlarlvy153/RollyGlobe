package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.rollyglobe.rollyglobe.response_model.NationCodeModel
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class SignUpActivity : AppCompatActivity() {
//    lateinit var compositeDisposable: CompositeDisposable
    val TAG = "LoginActivity_kgp"

    val nationCodeList = ArrayList<NationCodeModel>()
    val nationCodeStringList = ArrayList<String>()
//    val days = Array(28, {i -> i+1})
    val days = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28)
    lateinit var dayAdapter : ArrayAdapter<Int>
    lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val restClient:RollyGlobeApiInterface  =
            RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)

        val nationCode = restClient.getNationCodeInfoList()
        nationCode.enqueue(object : Callback<List<NationCodeModel>> {
                override fun onFailure(call: Call<List<NationCodeModel>>, t: Throwable) {
                    Log.d(TAG, t.localizedMessage)
                }

                override fun onResponse(call: Call<List<NationCodeModel>>,response: Response<List<NationCodeModel>>) {
                    Log.i(TAG, response.message().toString())
                    Log.i(TAG, response.body()!!.size.toString())
                    val body = response.body()
                    if(body != null){
                        for(code in body){
                            nationCodeStringList.add(code.toString())
                        }
                    }
                    initView()
                }
            }
        )


    }
    fun initView(){

        //nation code spinner
        val nationCodeSpinnerAdapter = ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item,nationCodeStringList)
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
        val yearSpinnerAdapter = ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item,yearList)
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

        //day spinner
        dayAdapter = ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        day_spinner.adapter = dayAdapter

        //month spinner
        val monthList = listOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val monthSpinnerAdapter = ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item, monthList)
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
            2-> if ((year%4 ==0) && (year % 100 != 0) || (year % 400 == 0)) 29 else 28
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



        val dayAdapter = ArrayAdapter(this@SignUpActivity, android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        day_spinner.adapter = dayAdapter
    }
    fun onClicksignup(v : View){
        val temp_email = signup_email_edit.text.toString()
        val temp_nickname = signup_nickname_edit.text.toString()
        val temp_password = signup_password.text.toString()
        val temp_password_again = signup_password_again.text.toString()
        val temp_gender_pos = gender_spinner.selectedItemPosition // 1남 2여
        val y = year_spinner.selectedItem.toString()
        val m = month_spinner.selectedItem.toString()
        val d = day_spinner.selectedItem.toString()
        val temp_nation = nation_code_spinner.selectedItem.toString()
        val contact = phone_number_edit.text.toString()

        Log.i(TAG,"$temp_email $temp_nickname $temp_password $temp_password_again")
        Log.i(TAG,"$temp_gender_pos $y $m $d")

        if(temp_password != temp_password_again){
            Log.e(TAG,"비밀번호가 다름 $temp_password $temp_password_again")
            return
        }
        if(temp_gender_pos == 0){
            Log.e(TAG,"성별 선택 안됨")
            return
        }

    }
}
