package com.globe.rolly.ui.my_page.edit_info_activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.globe.R
import com.globe.databinding.ActivityMyPageEditGenderBinding
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.edit_user_info.gender.EditUserGenderRequestModel
import com.globe.rolly.ui.my_page.ProfileEditActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class MyPageEditGenderActivity : AppCompatActivity() {
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()

    val genderList = ArrayList<String>()

    private lateinit var binding: ActivityMyPageEditGenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageEditGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_gender)

        genderList.add("Female")
        genderList.add("Male")


        val genderSpinnerAdapter =
            ArrayAdapter(this@MyPageEditGenderActivity, android.R.layout.simple_spinner_item, genderList)
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editGenderSpinner.adapter = genderSpinnerAdapter

        val userGender = intent.getStringExtra(ProfileEditActivity.EDIT_GENDER)
        if (userGender.equals("male")) {
            binding.editGenderSpinner.setSelection(1)
        }

    }

    fun onClickApplyBtn(v: View) {
        val requestModel = EditUserGenderRequestModel(
            binding.editGenderSpinner.selectedItem.toString().toLowerCase()
        )
//        Timber.d("${edit_gender_spinner.selectedItem.toString().toLowerCase()}")

        disposable.add(
            restClient.editUserGender(requestModel)
                .subscribe({ result ->
                    if (result.success) {
                        val intent = Intent()
                        intent.putExtra(ProfileEditActivity.EDIT_GENDER, binding.editGenderSpinner.selectedItem.toString().toLowerCase())
                        setResult(ProfileEditActivity.RESULT_CODE_GENDER, intent)
                        finish()
                    } else {
                        val intent = Intent()
//                    intent.putExtra(ProfileEditActivity.EDIT_NAME, newName)
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
