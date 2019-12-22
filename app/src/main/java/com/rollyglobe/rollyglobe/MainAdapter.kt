package com.rollyglobe.rollyglobe

import android.content.res.Resources
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainAdapter (fm : FragmentManager, resources: Resources) : FragmentStatePagerAdapter(fm){

    private val fragmentTitleList = resources.getStringArray(R.array.tab_items)

    override fun getItem(position:Int) : Fragment?{
        return when(position){
            0->RecommendFragment()
            1->CommunityFragment()
            2->GoodsFragment()
            3->MyPageFragment()
            else->null
        }
    }

    override fun getCount() = 4;

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }


}