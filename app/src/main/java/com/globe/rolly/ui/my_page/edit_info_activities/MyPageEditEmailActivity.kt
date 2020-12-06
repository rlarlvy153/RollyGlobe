package com.globe.rolly.ui.my_page.edit_info_activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.globe.R
import com.globe.databinding.ActivityMyPageEditEmailBinding
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.edit_user_info.email.EditUserEmailRequestModel
import com.globe.rolly.ui.my_page.ProfileEditActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class MyPageEditEmailActivity : AppCompatActivity() {

    lateinit var userEmail: String
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityMyPageEditEmailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageEditEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_email)

        userEmail = intent.getStringExtra(ProfileEditActivity.EDIT_EMAIL)
        binding.editTextEmail.setText(userEmail)
    }

    fun onClickApplyBtn(v: View) {
        val newEmail = binding.editTextEmail.text.toString()
        val requestModel = EditUserEmailRequestModel(newEmail)
        disposable.add(
            restClient.editUserEmail(requestModel)
                .subscribe({ result ->
                    if (result.success) {
                        val intent = Intent()
                        intent.putExtra(ProfileEditActivity.EDIT_EMAIL, newEmail)
                        setResult(ProfileEditActivity.RESULT_CODE_EMAIL, intent)
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
