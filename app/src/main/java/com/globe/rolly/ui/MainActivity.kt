package com.globe.rolly.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.globe.rolly.AppComponents
import com.globe.R
import com.globe.rolly.extensions.gone
import com.globe.rolly.extensions.visible
import com.globe.rolly.support.ScreenUtils
import com.globe.rolly.support.Utils
import com.globe.rolly.ui.community.CommunityFragment
import com.globe.rolly.ui.community.writepost.WritePostActivity
import com.globe.rolly.ui.goods.GoodsFragment
import com.globe.rolly.ui.home.HomeFragment
import com.globe.rolly.ui.my_page.MyPageFragment
import com.globe.rolly.ui.recommend.RecommendFragment
import com.globe.rolly.ui.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_each_tab.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private var actionMenu: Menu? = null

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.logouted.observe(this, Observer { logouted ->
            if (logouted) {
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        mainViewModel.showErrorMsg.observe(this, Observer {
            if (it.isNotEmpty() && it.isNotBlank()) {
                Utils.showToast(it)

                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
            }
        })


        initActionBar()

        initTab()

        selectTab(0)
    }

    private fun initTabListener() {
        mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            fun shadowToggle(tab: TabLayout.Tab) {
                val position: Int = tab.position
                if (position == 4) {
                    headerShadow.visibility = View.GONE
                } else {
                    headerShadow.visibility = View.VISIBLE
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

                setActionbarPosition(p0)

                invalidateOptionsMenu()
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
        setSupportActionBar(toolbar)

        supportActionBar?.run {
//            setIcon(R.drawable.logo_fullletter)
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
            elevation = 0f
            imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
            titleText.text = ""
        }


        communityWriteMenu.setOnClickListener {
            val intent = Intent(this@MainActivity, WritePostActivity::class.java)
            startActivity(intent)
        }
    }

    fun setActionbarPosition(tab : TabLayout.Tab){
        if(tab.position == 2){
            communityMenuContainer.visible()
        }
        else{
            communityMenuContainer.gone()
        }

        val tabRes = tab.tag as MainTabIconEnum
        val position: Int = tab.position

        if (position == 0) {
            supportActionBar?.let {
                imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
                val pix = ScreenUtils.dpToPixel(this@MainActivity, 140f)
                val param = imgLogo.layoutParams
                param.width = pix.toInt()
                imgLogo.layoutParams = param
                titleText.text = ""
            }
            supportActionBar?.title = ""


        } else {
            supportActionBar?.let {
                imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_icon))
                val pix = ScreenUtils.dpToPixel(this@MainActivity, 43f)
                val param = imgLogo.layoutParams
                param.width = pix.toInt()
                imgLogo.layoutParams = param

                titleText.text = AppComponents.applicationContext.getString(tabRes.title)
            }
        }



    }

    private fun setTabRes() {
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
            0 -> transaction.replace(R.id.mainContentsContainer, HomeFragment.instance)

            1 -> transaction.replace(R.id.mainContentsContainer, RecommendFragment.instance)

            2 -> transaction.replace(R.id.mainContentsContainer, CommunityFragment.instance)

            3 -> transaction.replace(R.id.mainContentsContainer, GoodsFragment.instance)

            4 -> transaction.replace(R.id.mainContentsContainer, MyPageFragment.instance)
        }
        transaction.commitNow()
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        Timber.d("kgp pre")
//        menu?.clear()
//
//        if (mainTab.selectedTabPosition == 2) {
//            menuInflater.inflate(R.menu.actionbar_actions_community, menu)
//        }
//        actionMenu = menu
//
//        return super.onPrepareOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        Timber.d("${item?.itemId}")
//
//        if(item?.itemId == R.id.action_search_post){
//            Utils.showToast("clicked search")
//        }
//        else if(item?.itemId == R.id.action_write_post){
//            val intent = Intent(this, WritePostActivity::class.java)
//            startActivity(intent)
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
}
