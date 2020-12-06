package com.globe.rolly.ui.my_page.edit_info_activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.globe.R
import com.globe.databinding.ActivityMyPageEditPhoneNumberBinding
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.edit_user_info.phonenumber.EditUserPhoneNumberRequestModel
import com.globe.rolly.ui.my_page.ProfileEditActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import timber.log.Timber

class MyPageEditPhoneNumberActivity : AppCompatActivity() {

    val nationCodeStringList = ArrayList<String>()

    lateinit var userPhoneNumber: String
    var userNationCode: Int = -1
    var userNationCodeIndex = 0;
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityMyPageEditPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageEditPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_phone_number)

        userPhoneNumber = intent.getStringExtra(ProfileEditActivity.EDIT_PHONENUMBER)
        userNationCode = intent.getIntExtra(ProfileEditActivity.EDIT_NATIONCODE, -1)
        Timber.d("nationcode $userNationCode")
        binding.editTextPhoneNumber.setText(userPhoneNumber)

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
        val newPhoneNumber = binding.editTextPhoneNumber.text.toString()
        val temp_nation = binding.mypageNationCodeSpinner.selectedItem.toString()
        val temp_trim_nation = temp_nation.substring(1, temp_nation.indexOf('(')).toInt()

        val requestModel =
            EditUserPhoneNumberRequestModel(newPhoneNumber, temp_trim_nation)
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
        binding.mypageNationCodeSpinner.adapter = nationCodeSpinnerAdapter
        binding.mypageNationCodeSpinner.setSelection(userNationCodeIndex)


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
