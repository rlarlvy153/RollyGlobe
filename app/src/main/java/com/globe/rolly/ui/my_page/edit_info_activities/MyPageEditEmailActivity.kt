package com.globe.rolly.ui.my_page.edit_info_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.globe.rolly.network.model.edit_user_info.email.EditUserEmailRequestModel
import com.globe.R
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.ui.my_page.ProfileEditActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_my_page_edit_email.*
import org.koin.android.ext.android.inject

class MyPageEditEmailActivity : AppCompatActivity() {

    lateinit var userEmail:String
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_edit_email)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_email)

        userEmail = intent.getStringExtra(ProfileEditActivity.EDIT_EMAIL)
        edit_text_email.setText(userEmail)
    }

    fun onClickApplyBtn(v : View){
        val newEmail = edit_text_email.text.toString()
        val requestModel = EditUserEmailRequestModel(newEmail)
        disposable.add(restClient.editUserEmail(requestModel)
            .subscribe({result->
                if(result.success){
                    val intent = Intent()
                    intent.putExtra(ProfileEditActivity.EDIT_EMAIL, newEmail)
                    setResult(ProfileEditActivity.RESULT_CODE_EMAIL, intent)
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
