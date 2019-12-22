package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {MainAdapter(supportFragmentManager,resources)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        contentViewPager.adapter = MainActivty@adapter
        main_tab.setupWithViewPager(contentViewPager)


    }
}
