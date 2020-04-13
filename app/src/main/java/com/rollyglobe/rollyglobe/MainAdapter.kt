package com.rollyglobe.rollyglobe

import android.content.res.Resources
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class MainAdapter (fm : FragmentManager, resources: Resources) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val fragmentTitleList = resources.getStringArray(R.array.tab_items)

    override fun getItem(position:Int) : Fragment{
        return when(position){
            1->RecommendFragment.instance
            2->CommunityFragment.instance
            3->GoodsFragment.instance
            4->MyPageFragment.instance
            else ->HomeFragment.instance

        }
    }

    override fun getCount() = fragmentTitleList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

}