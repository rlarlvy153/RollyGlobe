package com.rollyglobe.my_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.rollyglobe.network.model.request_model.EditUserEmailRequestModel
import com.rollyglobe.R
import com.rollyglobe.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_page_edit_email.*

class MyPageEditEmailActivity : AppCompatActivity() {

    lateinit var userEmail:String
    var restClient = RestClient.restClient
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
        disposable.add(restClient.EditUserEmail(requestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
