package com.rollyglobe.rollyglobe.MyPageFragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.rollyglobe.rollyglobe.R
import kotlinx.android.synthetic.main.activity_profile_edit.*

class ProfileEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.profile_edit)

        change_name.text = intent.getStringExtra("name")
        change_phone_number.text = intent.getStringExtra("phone_number")
        change_email.text = intent.getStringExtra("email")


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when(id){
            android.R.id.home ->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
