package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {MainAdapter(supportFragmentManager,resources)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        contentViewPager.adapter = MainActivty@adapter

        main_tab.setupWithViewPager(contentViewPager)

        main_tab.getTabAt(0)?.setIcon(R.drawable.recommend_selected)

        contentViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                main_tab.getTabAt(0)?.setIcon(R.drawable.recommend_icon)

                when(position) {

                    0   ->    main_tab.getTabAt(0)?.setIcon(R.drawable.recommend_selected)

                }

            }

        })
    }
}
