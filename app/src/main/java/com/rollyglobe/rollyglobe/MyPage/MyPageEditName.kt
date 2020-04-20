package com.rollyglobe.rollyglobe.MyPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import com.rollyglobe.rollyglobe.R
import kotlinx.android.synthetic.main.activity_my_page_edit_name.*

class MyPageEditName : AppCompatActivity() {

    lateinit var userName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_page_edit_name)

        userName = intent.getStringExtra(ProfileEditActivity.EDIT_NAME)
        edit_text_name.setText(userName)
    }

    fun onClickApplyBtn(v : View){
        val newName = edit_text_name.text.toString()



        val intent = Intent()
        intent.putExtra(ProfileEditActivity.EDIT_NAME, newName)
        setResult(ProfileEditActivity.RESULT_CODE_NAME, intent)
        finish()

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
