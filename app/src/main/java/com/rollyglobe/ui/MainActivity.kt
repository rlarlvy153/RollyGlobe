package com.rollyglobe.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.rollyglobe.AppComponent
import com.rollyglobe.R
import com.rollyglobe.ui.community.CommunityFragment
import com.rollyglobe.ui.goods.GoodsFragment
import com.rollyglobe.ui.home.HomeFragment
import com.rollyglobe.ui.my_page.MyPageFragment
import com.rollyglobe.ui.recommend.RecommendFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_each_tab.view.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private var actionMenu: Menu? = null

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.isLogin.observe(this, Observer { isLogin ->
            actionMenu?.let {
                actionMenu!!.findItem(R.id.action_login).isVisible = !isLogin
            }
        })

        setSupportActionBar(toolbar)

        initActionBar()

        initTab()

        selectTab(0)


        viewModel.getGeocodeByGps()

    }

    private fun initTabListener(){
        mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            fun shadowToggle(tab: TabLayout.Tab) {
                val position: Int = tab.position
                if (position == 4) {
                    upper_shadow.visibility = View.GONE
                } else {
                    upper_shadow.visibility = View.VISIBLE
                }
            }

            fun changeTitle(tab: TabLayout.Tab) {
                val tabRes = tab.tag as MainTabIconEnum
                val position: Int = tab.position

                if (position == 0) {
                    supportActionBar?.let {
                        img_logo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
                        title_text.text = ""
                    }
                    supportActionBar?.title = ""
                } else {
                    supportActionBar?.let {
                        img_logo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_icon))
                        title_text.text = AppComponent.applicationContext.getString(tabRes.title)
                    }
                }
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.let {
                    unselectTab(p0)
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0 == null) return

                selectTab(p0)

                shadowToggle(p0)

                changeTitle(p0)
            }
        })
    }

    fun unselectTab(tab: TabLayout.Tab) {
        val tabRes = tab.tag as MainTabIconEnum
        tab.customView?.icon?.setBackgroundResource(tabRes.unselected)
        tab.customView?.title?.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.tab_unselected))
    }

    fun selectTab(tab: TabLayout.Tab) {
        val tabRes = tab.tag as MainTabIconEnum
        tab.customView?.icon?.setBackgroundResource(tabRes.selected)
        tab.customView?.title?.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.tab_selected))

        callFragment(tab.position)
    }

    private fun selectTab(position: Int) {
        val selectedTab = mainTab.getTabAt(position) as TabLayout.Tab
        selectTab(selectedTab)
    }

    private fun initActionBar() {
        supportActionBar?.run {
//            setIcon(R.drawable.logo_fullletter)
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
            elevation = 0f
            img_logo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
            title_text.text = ""
        }
    }

    private fun setTabRes(){
        for (tab in MainTabIconEnum.values()) {
            val icon = tab.unselected
            val title = tab.title
            val v = layoutInflater.inflate(R.layout.main_each_tab, null)
            v.icon.setBackgroundResource(icon)
            v.title.setText(title)
            val newTab = mainTab.newTab()
            newTab.customView = v
            newTab.tag = tab
            mainTab.addTab(newTab)
        }
    }

    private fun initTab() {
        setTabRes()

        initTabListener()
    }

    private fun callFragment(position: Int?) {
        val transaction = supportFragmentManager.beginTransaction()
        if (position == null) return

        when (position) {
            0 -> transaction.replace(
                R.id.main_contents_container,
                HomeFragment.instance
            )
            1 -> transaction.replace(
                R.id.main_contents_container,
                RecommendFragment.instance
            )
            2 -> transaction.replace(
                R.id.main_contents_container,
                CommunityFragment.instance
            )
            3 -> transaction.replace(
                R.id.main_contents_container,
                GoodsFragment.instance
            )
            4 -> transaction.replace(
                R.id.main_contents_container,
                MyPageFragment.instance
            )
        }
        transaction.commitNow()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_actions, menu)
        actionMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //TODO logout
        Timber.d("${item?.itemId}")
        Timber.d("${R.id.action_login}")

        return super.onOptionsItemSelected(item)
    }
}
