package com.rollyglobe.ui.my_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.rollyglobe.network.model.request_model.EditUserNameRequestModel
import com.rollyglobe.R
import com.rollyglobe.network.RollyGlobeApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_page_edit_name.*
import org.koin.android.ext.android.inject
import org.koin.core.inject

class MyPageEditNameActivity : AppCompatActivity() {

    lateinit var userName:String
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_page_edit_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_name)
        userName = intent.getStringExtra(ProfileEditActivity.EDIT_NAME)
        edit_text_name.setText(userName)
    }

    fun onClickApplyBtn(v : View){
        val newName = edit_text_name.text.toString()
        val requestModel = EditUserNameRequestModel(newName)
        disposable.add(restClient.EditUserName(requestModel)
            .subscribe({result->
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
