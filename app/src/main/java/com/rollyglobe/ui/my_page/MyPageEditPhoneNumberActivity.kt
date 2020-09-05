package com.rollyglobe.ui.my_page

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.rollyglobe.R
import com.rollyglobe.network.RollyGlobeApiClient
import com.rollyglobe.network.model.request_model.EditUserPhoneNumberRequestModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_my_page_edit_phone_number.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MyPageEditPhoneNumberActivity : AppCompatActivity() {

    val nationCodeStringList = ArrayList<String>()

    lateinit var userPhoneNumber: String
    var userNationCode: Int = -1
    var userNationCodeIndex = 0;
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_edit_phone_number)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_phone_number)

        userPhoneNumber = intent.getStringExtra(ProfileEditActivity.EDIT_PHONENUMBER)
        userNationCode = intent.getIntExtra(ProfileEditActivity.EDIT_NATIONCODE, -1)
        Timber.d("nationcode $userNationCode")
        edit_text_phone_number.setText(userPhoneNumber)

        disposable.add(restClient.getNationCodeInfoList()
            .subscribe({ result ->

                for (code in result) {
                    nationCodeStringList.add(code.toString())
                    if (code.nationNum.toInt() == userNationCode) {
                        userNationCodeIndex = result.indexOf(code)
                    }
                }

                initView()
            }, {
                Timber.d("error")
            }

            )
        )
    }

    fun onClickApplyBtn(v: View) {
        val newPhoneNumber = edit_text_phone_number.text.toString()
        val temp_nation = mypage_nation_code_spinner.selectedItem.toString()
        val temp_trim_nation = temp_nation.substring(1, temp_nation.indexOf('(')).toInt()

        val requestModel = EditUserPhoneNumberRequestModel(newPhoneNumber, temp_trim_nation)
        disposable.add(
            restClient.editUserPhoneNumber(requestModel)
                .subscribe({ result ->
                    if (result.success) {
                        val intent = Intent()
                        intent.putExtra(ProfileEditActivity.EDIT_PHONENUMBER, newPhoneNumber)
                        setResult(ProfileEditActivity.RESULT_CODE_PHONENUMBER, intent)
                        finish()
                    } else {
                        val intent = Intent()
                        setResult(ProfileEditActivity.RESULT_CODE_FAIL, intent)
                        finish()
                    }


                }, {
                    it.printStackTrace()
                })
        )
    }

    fun initView() {
        val nationCodeSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nationCodeStringList
        )
        nationCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mypage_nation_code_spinner.adapter = nationCodeSpinnerAdapter
        mypage_nation_code_spinner.setSelection(userNationCodeIndex)


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
