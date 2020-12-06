package com.globe.rolly.ui.my_page.edit_info_activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.globe.R
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.edit_user_info.password.EditUserPasswordRequestModel
import com.globe.rolly.ui.my_page.ProfileEditActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_my_page_edit_password.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MyPageEditPasswordActivity : AppCompatActivity() {
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_edit_password)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_password)
    }

    fun onClickApplyBtn(v: View) {
        Timber.d("current ${edit_text_previous_password.text.toString()}")
        Timber.d("new ${edit_text_new_password.text.toString()}")
        Timber.d("new 2 ${edit_text_new_password_again.text.toString()}")

        val requestModel = EditUserPasswordRequestModel(
            edit_text_previous_password.text.toString(),
            edit_text_new_password.text.toString()
        )
        disposable.add(
            restClient.editUserPassword(requestModel)
                .subscribe({ result ->
                    if (result.success) {
                        val intent = Intent()
//                    intent.putExtra(ProfileEditActivity.EDIT_NAME, newName)
                        setResult(ProfileEditActivity.RESULT_CODE_PASSWARD, intent)
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
