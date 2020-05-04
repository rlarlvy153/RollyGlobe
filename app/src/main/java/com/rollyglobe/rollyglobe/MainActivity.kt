package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_each_tab.view.*
import kotlin.collections.ArrayList
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { MainAdapter(supportFragmentManager, resources) }
    private lateinit var titleArray: ArrayList<String>
    private var iconArray = listOf(
        R.drawable.home,
        R.drawable.recommendation,
        R.drawable.community,
        R.drawable.product,
        R.drawable.mypage
    )
    private var iconArraySelected = listOf(
        R.drawable.home_ccolor,
        R.drawable.recommendation_ccolor,
        R.drawable.community_ccolor,
        R.drawable.product_ccolor,
        R.drawable.mypage_ccolor
    )
    private var actionMenu : Menu? = null
    lateinit private var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.isLogin.observe(this,Observer{isLogin->
            actionMenu?.let{
                actionMenu!!.findItem(R.id.action_login).isVisible = !isLogin
            }
        })


//        supportActionBar?.run{
//            setIcon(R.drawable.logo_fullletter)
//            setDisplayUseLogoEnabled(true)
//            setDisplayShowHomeEnabled(true)
//            title = ""
//        }

        titleArray = ArrayList<String>(resources.getStringArray(R.array.tab_items).toMutableList())


        contentViewPager.adapter = adapter
        main_tab.setupWithViewPager(contentViewPager)

        for (i in iconArray.indices) {
            val icon = iconArray[i]
            val title = titleArray[i]
            val v = layoutInflater.inflate(R.layout.main_each_tab, null)
            v.icon.setBackgroundResource(icon)
            v.title.setText(title)
            main_tab.getTabAt(i)?.customView = v
        }


        main_tab.getTabAt(0)?.customView?.icon?.setBackgroundResource(iconArraySelected[0])
        main_tab.getTabAt(0)?.customView?.title?.setTextColor(resources.getColor(R.color.tab_selected))

        contentViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Timber.d("onPageScrollStateChanged $state")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                Timber.d("page $position")
                contentViewPager.isVisible = true
                for (i in iconArray.indices) {
                    main_tab.getTabAt(i)?.customView?.icon?.setBackgroundResource(iconArray[i])
                    main_tab.getTabAt(i)?.customView?.title?.setTextColor(resources.getColor(R.color.tab_unselected))
                }
                main_tab.getTabAt(position)?.customView?.let {
                    it.icon.setBackgroundResource(iconArraySelected[position])
                    it.title.setTextColor(resources.getColor(R.color.tab_selected))
                }
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_actions,menu)
        actionMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Timber.d("${item?.itemId}")
        Timber.d("${R.id.action_login}")

//        when(item?.itemId){
//            R.id.action_login -> Intent(this,SignInActivity::class.java)
//
//        }
        return super.onOptionsItemSelected(item)
    }
}
