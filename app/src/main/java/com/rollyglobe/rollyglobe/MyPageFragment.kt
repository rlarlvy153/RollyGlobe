package com.rollyglobe.rollyglobe


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rollyglobe.rollyglobe.MyPage.MyPageAdapter
import kotlinx.android.synthetic.main.mypage_each_tab.view.*
import kotlinx.android.synthetic.main.mypage_fragment.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment() {

    companion object {
        val instance = MyPageFragment()
    }

    private val adapter by lazy { MyPageAdapter(childFragmentManager, resources) }
    private lateinit var titleArray: ArrayList<String>

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("life onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("life onCreateView")
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.mypage_fragment, container, false)
        viewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)

        val tabLayout = root.findViewById<TabLayout>(R.id.mypage_tab)
        val viewPager = root.findViewById<ViewPager>(R.id.mypage_content_viewpager)
//        viewPager.adapter = adapter

        titleArray = ArrayList<String>(resources.getStringArray(R.array.mypage_tab_items).toMutableList())
        viewPager.adapter = MyPageAdapter(childFragmentManager, resources)
        tabLayout.setupWithViewPager(viewPager)
        for (i in 0 until titleArray.size) {

            val v = layoutInflater.inflate(R.layout.mypage_each_tab, tabLayout, false)
            val title = titleArray[i]
            val titleView = v.findViewById<TextView>(R.id.mypage_tab_title)
            v.layoutParams.height = 60
            titleView.setText(title)
            tabLayout.getTabAt(i)?.customView = v

        }
        

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

                val title = p0?.customView?.findViewById<TextView>(R.id.mypage_tab_title)
                title!!.setTextColor(ContextCompat.getColor(activity!!, R.color.rg_gray))
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {

                val title = p0?.customView?.findViewById<TextView>(R.id.mypage_tab_title)
                title!!.setTextColor(ContextCompat.getColor(activity!!, R.color.rg_blue))
                viewPager.setCurrentItem(p0!!.position)


            }
        })
        tabLayout.getTabAt(0)?.customView?.findViewById<TextView>(R.id.mypage_tab_title)?.setTextColor(ContextCompat.getColor(activity!!, R.color.rg_blue))

        return root
    }


}
