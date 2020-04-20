package com.rollyglobe.rollyglobe.MyPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import com.rollyglobe.rollyglobe.MainActivity
import com.rollyglobe.rollyglobe.Model.request_model.EditUserNameRequestModel
import com.rollyglobe.rollyglobe.Model.response_model.EditUserNameResponseModel
import com.rollyglobe.rollyglobe.R
import com.rollyglobe.rollyglobe.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_page_edit_name.*
import timber.log.Timber

class MyPageEditName : AppCompatActivity() {

    lateinit var userName:String
    var restClient = RestClient.restClient
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_page_edit_name)

        userName = intent.getStringExtra(ProfileEditActivity.EDIT_NAME)
        edit_text_name.setText(userName)
    }

    fun onClickApplyBtn(v : View){
        val newName = edit_text_name.text.toString()
        val requestModel = EditUserNameRequestModel(newName)
        disposable.add(restClient.EditUserName(requestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result->
                Timber.d("result ${result.success}")
                if(result.success){
                    val intent = Intent()
                    intent.putExtra(ProfileEditActivity.EDIT_NAME, newName)
                    setResult(ProfileEditActivity.RESULT_CODE_NAME, intent)
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
