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

        Log.i(TAG, "asdf")


    }
    fun initView(){
        val nationCodeSpinnerAdapter = ArrayAdapter(this@LoginActivity, android.R.layout.simple_spinner_item,nationCodeStringList)
        nationCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nation_code_spinner.adapter = nationCodeSpinnerAdapter

        nation_code_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.i(TAG,"not selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
                Log.i(TAG,"selected $position ${nationCodeStringList[position]}")
            }
        }

    }
}
