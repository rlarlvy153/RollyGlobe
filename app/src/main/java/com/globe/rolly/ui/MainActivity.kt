package com.globe.rolly.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.globe.rolly.AppComponents
import com.globe.R
import com.globe.rolly.support.Utils
import com.globe.rolly.ui.community.CommunityFragment
import com.globe.rolly.ui.goods.GoodsFragment
import com.globe.rolly.ui.home.HomeFragment
import com.globe.rolly.ui.my_page.MyPageFragment
import com.globe.rolly.ui.recommend.RecommendFragment
import com.globe.rolly.ui.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_each_tab.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    companion object{
        val IS_SIGN_IN_KEY = "isSignIn"
    }
    private var actionMenu: Menu? = null

    private val mainViewModel: MainViewModel by viewModel()

    private var isSignIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("viewmodel id " +mainViewModel.hashCode())

        mainViewModel.logouted.observe(this, Observer { logouted ->
            Timber.d("main acitivtiy observe")
            Timber.d("viewmodel id " +mainViewModel.hashCode())
            if(logouted){
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        mainViewModel.showErrorMsg.observe(this,Observer{
            if(it.isNotEmpty() && it.isNotBlank()){
                Utils.showToast(Utils.getString(R.string.signin_require_login))

                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)

//                finish()
            }
        })

        setSupportActionBar(toolbar)

        initActionBar()

        initTab()

        selectTab(0)

    }

    override fun onResume() {
        super.onResume()

        Timber.d("kgp onresume")
        isSignIn = intent.extras?.getBoolean(IS_SIGN_IN_KEY, false) ?: false

        if(isSignIn){
            Timber.d("sign in $isSignIn")
            actionMenu?.findItem(R.id.action_login)?.isVisible = false
        }

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

            fun changeTitle(tab: TabLayout.Tab) {
                val tabRes = tab.tag as MainTabIconEnum
                val position: Int = tab.position

                if (position == 0) {
                    supportActionBar?.let {
                        imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
                        titleText.text = ""
                    }
                    supportActionBar?.title = ""
                } else {
                    supportActionBar?.let {
                        imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_icon))
                        titleText.text = AppComponents.applicationContext.getString(tabRes.title)
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
            imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
            titleText.text = ""
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Timber.d("kgp onCreateOptionMenu")
        if(isSignIn){
            return super.onCreateOptionsMenu(menu)

        }
        menuInflater.inflate(R.menu.actionbar_actions, menu)
        actionMenu = menu

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Timber.d("${item?.itemId}")
        Timber.d("${R.id.action_login}")

        if (item?.itemId == R.id.action_login) {
            val intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)

    }
}
