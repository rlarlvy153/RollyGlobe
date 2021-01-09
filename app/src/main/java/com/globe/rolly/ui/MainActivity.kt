package com.globe.rolly.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.globe.R
import com.globe.databinding.ActivityMainBinding
import com.globe.databinding.MainEachTabBinding
import com.globe.rolly.AppComponents
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
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private val mainViewPagerAdapter = MainViewPagerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.logouted.observe(this, { logouted ->
            if (logouted) {
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        mainViewModel.showErrorMsg.observe(this, {
            if (it.isNotEmpty() && it.isNotBlank()) {
                Utils.showToast(it)

                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
            }
        })


        initActionBar()

        initTab()

        initMainViewPager()

        selectTab(0)
    }

    private fun initTabListener() {
        binding.mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            fun shadowToggle(tab: TabLayout.Tab) {
                val position: Int = tab.position
                if (position == 4) {
                    binding.headerShadow.visibility = View.GONE
                } else {
                    binding.headerShadow.visibility = View.VISIBLE
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
        val customTab = tab.customView!!
        val customTabBinding = MainEachTabBinding.bind(customTab)

        customTabBinding.icon.setBackgroundResource(tabRes.unselected)
        customTabBinding.title.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.tab_unselected))
    }

    fun selectTab(tab: TabLayout.Tab) {
        val tabRes = tab.tag as MainTabIconEnum
        val customTab = tab.customView!!
        val customTabBinding = MainEachTabBinding.bind(customTab)

        customTabBinding.icon.setBackgroundResource(tabRes.selected)
        customTabBinding.title.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.tab_selected))

        callFragment(tab.position)
    }

    private fun selectTab(position: Int) {

        val selectedTab = binding.mainTab.getTabAt(position) as TabLayout.Tab
        selectTab(selectedTab)
    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.run {
//            setIcon(R.drawable.logo_fullletter)
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
            elevation = 0f
            binding.imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
            binding.titleText.text = ""
        }


        binding.communityWriteMenu.setOnClickListener {
            val intent = Intent(this@MainActivity, WritePostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initMainViewPager(){
        binding.mainViewPager.run{
            isUserInputEnabled = false
            adapter = mainViewPagerAdapter
        }
    }

    fun setActionbarPosition(tab: TabLayout.Tab) {
        if (tab.position == 2) {
            binding.communityMenuContainer.visible()
        } else {
            binding.communityMenuContainer.gone()
        }

        val tabRes = tab.tag as MainTabIconEnum
        val position: Int = tab.position

        if (position == 0) {
            supportActionBar?.let {
                binding.imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_fullletter))
                val pix = ScreenUtils.dpToPixel(this@MainActivity, 140f)
                val param = binding.imgLogo.layoutParams
                param.width = pix.toInt()
                binding.imgLogo.layoutParams = param
                binding.titleText.text = ""
            }
            supportActionBar?.title = ""


        } else {
            supportActionBar?.let {
                binding.imgLogo.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.logo_icon))
                val pix = ScreenUtils.dpToPixel(this@MainActivity, 43f)
                val param = binding.imgLogo.layoutParams
                param.width = pix.toInt()
                binding.imgLogo.layoutParams = param

                binding.titleText.text = AppComponents.applicationContext.getString(tabRes.title)
            }
        }
    }

    private fun setTabRes() {
        for (tab in MainTabIconEnum.values()) {
            val icon = tab.unselected
            val title = tab.title
            val v = MainEachTabBinding.inflate(layoutInflater)
//            val v = layoutInflater.inflate(R.layout.main_each_tab, null)
            v.icon.setBackgroundResource(icon)
            v.title.setText(title)
            val newTab = binding.mainTab.newTab()
            newTab.customView = v.root
            newTab.tag = tab
            binding.mainTab.addTab(newTab)
        }
    }

    private fun initTab() {
        setTabRes()

        initTabListener()
    }

    private fun callFragment(position: Int) {
        binding.mainViewPager.setCurrentItem(position, false)
    }

}
