package com.rollyglobe.rollyglobe.MyPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rollyglobe.rollyglobe.R
import kotlinx.android.synthetic.main.activity_my_page_edit_name.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import timber.log.Timber

class ProfileEditActivity : AppCompatActivity() {

    companion object {
        val EDIT_NAME = "name"
        val EDIT_PHONENUMBER = "phone_number"
        val EDIT_EMAIL = "email"
        val EDIT_BIRTHDAY = "birthday"
        val EDIT_GENDER = "gender"

        val EDIT_EACH_PROFILE_REQUEST_CODE = 1
        val RESULT_CODE_NAME = 1001
        val RESULT_CODE_PHONENUMBER = 1002
        val RESULT_CODE_EMAIL = 1003
        val RESULT_CODE_BIRTHDAY = 1004
        val RESULT_CODE_GENDER = 1005
        val RESULT_CODE_PASSWARD = 1006
        val RESULT_CODE_FAILE = 1000
    }

    lateinit var name: String
    lateinit var viewModel: ProfileEditViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.profile_edit)

        viewModel = ViewModelProvider(this).get(ProfileEditViewModel::class.java)
        viewModel.setName(intent.getStringExtra(EDIT_NAME))
        viewModel.setPhoneNumber(intent.getStringExtra(EDIT_PHONENUMBER))
        viewModel.setEmail(intent.getStringExtra(EDIT_EMAIL))
        viewModel.setBirthday(intent.getStringExtra(EDIT_BIRTHDAY))
        viewModel.setGender(intent.getStringExtra(EDIT_GENDER))

        viewModel.userName.observe(this, Observer { name ->
            change_name.text = name
        })
        viewModel.userPhoneNumber.observe(this, Observer { phoneNumber ->
            change_phone_number.text = phoneNumber
        })
        viewModel.userEmail.observe(this, Observer { email ->
            change_email.text = email

        })
        viewModel.userBirthday.observe(this, Observer { birthday ->
            change_birthday.text = birthday
        })
        viewModel.userGender.observe(this, Observer { gender ->
            change_gender.text = gender
        })
    }

    fun onClickEditEachAttribute(v: View) {
        lateinit var intent: Intent
        when (v.id) {
            R.id.change_name -> {
                intent = Intent(this, MyPageEditName::class.java)
                intent.putExtra(EDIT_NAME, viewModel.userName.value)
            }
            R.id.change_phone_number -> null
            R.id.change_email -> null
            R.id.change_password -> null
            R.id.change_birthday -> null
            R.id.change_gender -> null
        }
        startActivityForResult(intent, EDIT_EACH_PROFILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("return activity $requestCode $resultCode")
        if (requestCode == EDIT_EACH_PROFILE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_CODE_NAME -> {
                    val resultName = data?.getStringExtra(EDIT_NAME)
                    resultName?.let{
                        viewModel.setName(resultName)

                    }
                }
                RESULT_CODE_EMAIL -> null
                RESULT_CODE_PASSWARD -> null
                RESULT_CODE_BIRTHDAY -> null
                RESULT_CODE_GENDER -> null

            }
        }
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
