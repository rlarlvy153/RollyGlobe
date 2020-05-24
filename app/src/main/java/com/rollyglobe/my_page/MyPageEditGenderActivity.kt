package com.rollyglobe.my_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.rollyglobe.network.model.request_model.EditUserGenderRequestModel
import com.rollyglobe.R
import com.rollyglobe.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_page_edit_gender.*

class MyPageEditGenderActivity : AppCompatActivity() {
    var restClient = RestClient.restClient
    private val disposable = CompositeDisposable()

    val genderList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_edit_gender)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_gender)

        genderList.add("Female")
        genderList.add("Male")



        val genderSpinnerAdapter =
            ArrayAdapter(this@MyPageEditGenderActivity, android.R.layout.simple_spinner_item, genderList)
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edit_gender_spinner.adapter = genderSpinnerAdapter

        val userGender = intent.getStringExtra(ProfileEditActivity.EDIT_GENDER)
        if(userGender.equals("male")){
            edit_gender_spinner.setSelection(1)
        }

    }
    fun onClickApplyBtn(v : View){
        val requestModel = EditUserGenderRequestModel(edit_gender_spinner.selectedItem.toString().toLowerCase())
//        Timber.d("${edit_gender_spinner.selectedItem.toString().toLowerCase()}")

        disposable.add(restClient.EditUserGender(requestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result->
                if(result.success){
                    val intent = Intent()
                    intent.putExtra(ProfileEditActivity.EDIT_GENDER, edit_gender_spinner.selectedItem.toString().toLowerCase())
                    setResult(ProfileEditActivity.RESULT_CODE_GENDER, intent)
                    finish()
                }
                else{
                    val intent = Intent()
//                    intent.putExtra(ProfileEditActivity.EDIT_NAME, newName)
                    setResult(ProfileEditActivity.RESULT_CODE_FAIL, intent)
                    finish()
                }


            },{

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
