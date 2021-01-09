package com.globe.rolly.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.globe.rolly.ui.community.CommunityFragment
import com.globe.rolly.ui.goods.GoodsFragment
import com.globe.rolly.ui.home.HomeFragment
import com.globe.rolly.ui.my_page.MyPageHomeFragment
import com.globe.rolly.ui.recommend.RecommendFragment

import java.util.*

class MainViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val backStack = Stack<Int>()

    override fun getItemCount(): Int {
        return MainTabIconEnum.values().size
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1 -> RecommendFragment.instance
            2 -> CommunityFragment.instance
            3 -> GoodsFragment.instance
            4 -> MyPageHomeFragment.instance
            else -> HomeFragment.instance
        }
    }

    fun addBackStack(position: Int) {
        if (backStack.contains(position)) {
            backStack.remove(position)
        }
        backStack.push(position)
    }

    fun isEmptyBackStack(): Boolean = backStack.isEmpty()

    fun popBackStack(): Int = backStack.pop()

    fun peekBackStack(): Int = backStack.peek()


}