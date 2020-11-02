package com.globe.rolly.ui.my_page

import android.content.res.Resources
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.globe.R

class MyPageAdapter (fm : FragmentManager, resources: Resources) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val fragmentTitleList = resources.getStringArray(R.array.mypage_tab_items)

    override fun getItem(position:Int) : Fragment{
        return when(position){
            1->MyPageScheduleFragment.instance
            2->MyPageBookFragment.instance
            3->MyPageActivityFragment.instance
            else ->MyPageHomeFragment.instance
        }
    }

    override fun getCount() = fragmentTitleList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
//        return super.getPageTitle(position)
    }

}