package com.globe.rolly.ui.my_page.edit_info_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.globe.rolly.network.model.edit_user_info.name.EditUserNameRequestModel
import com.globe.R
import com.globe.databinding.ActivityMyPageEditNameBinding
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.ui.my_page.ProfileEditActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class MyPageEditNameActivity : AppCompatActivity() {

    lateinit var userName:String
    val restClient: RollyGlobeApiClient by inject()
    private val disposable = CompositeDisposable()

    private lateinit var binding : ActivityMyPageEditNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageEditNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_name)
        userName = intent.getStringExtra(ProfileEditActivity.EDIT_NAME)
        binding.editTextName.setText(userName)
    }

    fun onClickApplyBtn(v : View){
        val newName = binding.editTextName.text.toString()
        val requestModel = EditUserNameRequestModel(newName)
        disposable.add(restClient.editUserName(requestModel)
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
