package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_each_tab.view.*
import kotlin.collections.ArrayList
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {MainAdapter(supportFragmentManager,resources)}
    private lateinit var titleArray : ArrayList<String>
    private var iconArray = listOf(R.drawable.recommendation, R.drawable.community, R.drawable.product, R.drawable.mypage)
    private var iconArraySelected = listOf(R.drawable.recommendation_ccolor, R.drawable.community, R.drawable.product_ccolor, R.drawable.mypage_ccolor)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleArray = ArrayList<String>(resources.getStringArray(R.array.tab_items).toMutableList())

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


        contentViewPager.adapter = MainActivty@adapter
        main_tab.setupWithViewPager(contentViewPager)

        for( i in iconArray.indices){
            val icon = iconArray[i]
            val title = titleArray[i]
            val v = layoutInflater.inflate(R.layout.main_each_tab,null)
            v.icon.setBackgroundResource(icon)
            v.title.setText(title)
            main_tab.getTabAt(i)?.customView = v
        }



//        main_tab.getTabAt(0)?.setIcon(iconArray[0])
        main_tab.getTabAt(0)?.customView?.icon?.setBackgroundResource(iconArraySelected[0])
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
                for( i in iconArray.indices){
                    main_tab.getTabAt(i)?.customView?.icon?.setBackgroundResource(iconArray[i])
                }
                main_tab.getTabAt(position)?.customView?.icon?.setBackgroundResource(iconArraySelected[position])
            }
        })


    }
}
