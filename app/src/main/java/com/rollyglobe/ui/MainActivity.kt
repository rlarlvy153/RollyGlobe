package com.rollyglobe.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.rollyglobe.*
import com.rollyglobe.ui.community.CommunityFragment
import com.rollyglobe.ui.goods.GoodsFragment
import com.rollyglobe.ui.home.HomeFragment
import com.rollyglobe.ui.my_page.MyPageFragment
import com.rollyglobe.ui.recommend.RecommendFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_each_tab.view.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        MainAdapter(
            supportFragmentManager,
            resources
        )
    }
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

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.run{
//            setIcon(R.drawable.logo_fullletter)
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
            elevation = 0f
            val logoImage = findViewById< ImageView>(R.id.img_logo)
            logoImage.setImageDrawable(resources.getDrawable(R.drawable.logo_fullletter))

            val titleText = findViewById<TextView>(R.id.title_text)
            titleText.setText("")
        }

        titleArray = ArrayList<String>(resources.getStringArray(R.array.tab_items).toMutableList())


//        contentViewPager.adapter = adapter
//        main_tab.setupWithViewPager(contentViewPager)
//        main_tab.setOnTouchListener(object : View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                return false
//            }
//        })


        for (i in iconArray.indices) {
            val icon = iconArray[i]
            val title = titleArray[i]
            val v = layoutInflater.inflate(R.layout.main_each_tab, null)
            v.icon.setBackgroundResource(icon)
            v.title.setText(title)
            main_tab.addTab(main_tab.newTab())
            main_tab.getTabAt(i)?.customView = v
        }


        main_tab.getTabAt(0)?.customView?.icon?.setBackgroundResource(iconArraySelected[0])
        main_tab.getTabAt(0)?.customView?.title?.setTextColor(resources.getColor(R.color.tab_selected))
        callFragment(0)
        main_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                callFragment(p0?.position)
                val position : Int = p0!!.position
                if(position == 4) {
                    upper_shadow.visibility = View.GONE
                }else {
                    upper_shadow.visibility = View.VISIBLE
                }
                for (i in iconArray.indices) {
                    main_tab.getTabAt(i)?.customView?.icon?.setBackgroundResource(iconArray[i])
                    main_tab.getTabAt(i)?.customView?.title?.setTextColor(resources.getColor(
                        R.color.tab_unselected
                    ))
                }
                main_tab.getTabAt(position)?.customView?.let {
                    it.icon.setBackgroundResource(iconArraySelected[position])
                    it.title.setTextColor(resources.getColor(R.color.tab_selected))
                }
                if(position == 0) {
                    supportActionBar?.let {
                        val logoImage = findViewById< ImageView>(R.id.img_logo)
                        logoImage.setImageDrawable(resources.getDrawable(R.drawable.logo_fullletter))

                        val titleText = findViewById<TextView>(R.id.title_text)
                        titleText.setText("")

                    }
                    supportActionBar?.setTitle("")
                }else {
                    supportActionBar?.let {
                        val logoImage = findViewById< ImageView>(R.id.img_logo)
                        logoImage.setImageDrawable(resources.getDrawable(R.drawable.logo_icon))

                        val titleText = findViewById<TextView>(R.id.title_text)
                        titleText.setText(titleArray[position])
                    }

                }
            }
        })

//        contentViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//                Timber.d("onPageScrollStateChanged $state")
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//
//            }
//
//            override fun onPageSelected(position: Int) {
//                Timber.d("page $position")
//                callFragment(position)
//                for (i in iconArray.indices) {
//                    main_tab.getTabAt(i)?.customView?.icon?.setBackgroundResource(iconArray[i])
//                    main_tab.getTabAt(i)?.customView?.title?.setTextColor(resources.getColor(R.color.tab_unselected))
//                }
//                main_tab.getTabAt(position)?.customView?.let {
//                    it.icon.setBackgroundResource(iconArraySelected[position])
//                    it.title.setTextColor(resources.getColor(R.color.tab_selected))
//                }
//                if(position == 0) {
//                    supportActionBar?.let {
//                        val logoImage = findViewById< ImageView>(R.id.img_logo)
//                        logoImage.setImageDrawable(resources.getDrawable(R.drawable.logo_fullletter))
//
//                        val titleText = findViewById<TextView>(R.id.title_text)
//                        titleText.setText("")
//
//                    }
//                    supportActionBar?.setTitle("")
//                }else {
//                    supportActionBar?.let {
//                        val logoImage = findViewById< ImageView>(R.id.img_logo)
//                        logoImage.setImageDrawable(resources.getDrawable(R.drawable.logo_icon))
//
//
//
//                        val titleText = findViewById<TextView>(R.id.title_text)
//                        titleText.setText(titleArray[position])
//                    }
//
//                }
//            }
//        })


    }
    fun callFragment(position:Int?){
        val transaction = supportFragmentManager.beginTransaction()
        if(position == null) return

        when(position){
            0->transaction.replace(
                R.id.main_contents_container,
                HomeFragment.instance
            )
            1->transaction.replace(
                R.id.main_contents_container,
                RecommendFragment.instance
            )
            2->transaction.replace(
                R.id.main_contents_container,
                CommunityFragment.instance
            )
            3->transaction.replace(
                R.id.main_contents_container,
                GoodsFragment.instance
            )
            4->transaction.replace(
                R.id.main_contents_container,
                MyPageFragment.instance
            )
        }
        transaction.commitNow()
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
