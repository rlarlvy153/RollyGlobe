package com.rollyglobe.rollyglobe


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rollyglobe.rollyglobe.MyPage.MyPageAdapter
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment() {

    companion object {
        val instance = MyPageFragment()
    }

    private val adapter by lazy { MyPageAdapter(childFragmentManager, resources) }

    lateinit var viewModel : MainViewModel
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
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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

            }
        })



        return root
    }


}
