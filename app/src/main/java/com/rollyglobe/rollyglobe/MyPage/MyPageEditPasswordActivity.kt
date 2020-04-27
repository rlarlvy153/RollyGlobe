package com.rollyglobe.rollyglobe.MyPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rollyglobe.rollyglobe.R

class MyPageEditPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_edit_password)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backwardarrow_ccolor)
        supportActionBar?.setTitle(R.string.title_edit_password)
    }
}
