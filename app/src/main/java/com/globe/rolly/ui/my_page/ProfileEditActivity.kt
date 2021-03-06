package com.globe.rolly.ui.my_page

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.globe.R
import com.globe.databinding.ActivityProfileEditBinding
import com.globe.rolly.ui.my_page.edit_info_activities.*

class ProfileEditActivity : AppCompatActivity() {

    companion object {
        const val EDIT_NAME = "name"
        const val EDIT_PHONENUMBER = "phone_number"
        const val EDIT_NATIONCODE = "nation_code"
        const val EDIT_EMAIL = "email"
        const val EDIT_BIRTHDAY = "birthday"
        const val EDIT_GENDER = "gender"

        const val EDIT_EACH_PROFILE_REQUEST_CODE = 1
        const val RESULT_CODE_NAME = 1001
        const val RESULT_CODE_PHONENUMBER = 1002
        const val RESULT_CODE_EMAIL = 1003
        const val RESULT_CODE_BIRTHDAY = 1004
        const val RESULT_CODE_GENDER = 1005
        const val RESULT_CODE_PASSWARD = 1006
        const val RESULT_CODE_FAIL = 1000
    }

    lateinit var name: String
    lateinit var viewModel: ProfileEditViewModel

    private lateinit var binding: ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
            setTitle(R.string.profile_edit)
        }

        viewModel = ViewModelProvider(this).get(ProfileEditViewModel::class.java)

        setUserData()

        observeEvents()
    }

    private fun setUserData() {
        viewModel.setName(intent.getStringExtra(EDIT_NAME))
        viewModel.setPhoneNumber(intent.getStringExtra(EDIT_PHONENUMBER))
        viewModel.setEmail(intent.getStringExtra(EDIT_EMAIL))
        viewModel.setBirthday(intent.getStringExtra(EDIT_BIRTHDAY))
        viewModel.setGender(intent.getStringExtra(EDIT_GENDER))
        viewModel.userNationCode = intent.getIntExtra(EDIT_NATIONCODE, -1)
    }

    private fun observeEvents() {
        viewModel.userName.observe(this, { name ->
            binding.userName.text = name
        })
        viewModel.userPhoneNumber.observe(this, { phoneNumber ->
            binding.userPhoneNumber.text = phoneNumber
        })
        viewModel.userEmail.observe(this, { email ->
            binding.userEmail.text = email

        })
        viewModel.userBirthday.observe(this, { birthday ->
            binding.userBirthday.text = birthday
        })
        viewModel.userGender.observe(this, { gender ->
            binding.userGender.text = gender
        })
    }

    fun onClickEditEachAttribute(v: View) {
        lateinit var intent: Intent
        when (v.id) {
            R.id.change_name -> {
                intent = Intent(this, MyPageEditNameActivity::class.java)
                intent.putExtra(EDIT_NAME, viewModel.userName.value)
            }
            R.id.change_phone_number -> {
                intent = Intent(this, MyPageEditPhoneNumberActivity::class.java)
                intent.putExtra(EDIT_NATIONCODE, viewModel.userNationCode)
                intent.putExtra(EDIT_PHONENUMBER, viewModel.userPhoneNumber.value)
            }
            R.id.change_email -> {
                intent = Intent(this, MyPageEditEmailActivity::class.java)
                intent.putExtra(EDIT_EMAIL, viewModel.userEmail.value)
            }
            R.id.change_password -> {
                intent = Intent(this, MyPageEditPasswordActivity::class.java)

            }
            R.id.change_birthday -> {
                intent = Intent(this, MyPageEditBirthdayActivity::class.java)
                intent.putExtra(EDIT_BIRTHDAY, viewModel.userBirthday.value)
            }
            R.id.change_gender -> {
                intent = Intent(this, MyPageEditGenderActivity::class.java)
                intent.putExtra(EDIT_GENDER, viewModel.userGender.value)

            }
        }
        startActivityForResult(intent, EDIT_EACH_PROFILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_EACH_PROFILE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_CODE_NAME -> {
                    val resultName = data?.getStringExtra(EDIT_NAME)
                    resultName?.let {
                        viewModel.setName(resultName)

                    }
                }
                RESULT_CODE_PHONENUMBER -> {
                    val resultPhoneNumber = data?.getStringExtra(EDIT_PHONENUMBER)
                    resultPhoneNumber?.let {
                        viewModel.setPhoneNumber(resultPhoneNumber)
                    }
                }
                RESULT_CODE_EMAIL -> {
                    val resultEmail = data?.getStringExtra(EDIT_EMAIL)
                    resultEmail?.let {
                        viewModel.setEmail(resultEmail)
                    }
                }
                RESULT_CODE_PASSWARD -> null
                RESULT_CODE_BIRTHDAY -> {
                    val resultBirthday = data?.getStringExtra(EDIT_BIRTHDAY)
                    resultBirthday?.let {
                        viewModel.setBirthday(resultBirthday)
                    }
                }
                RESULT_CODE_GENDER -> {
                    val resultGender = data?.getStringExtra(EDIT_GENDER)
                    resultGender?.let {
                        viewModel.setGender(resultGender)
                    }
                }

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
