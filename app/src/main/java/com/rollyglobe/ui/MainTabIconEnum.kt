package com.rollyglobe.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rollyglobe.R

enum class MainTabIconEnum(@StringRes val title: Int, @DrawableRes val unselected: Int, @DrawableRes val selected: Int) {

    HOME(R.string.tab_title_home,R.drawable.home, R.drawable.home_ccolor),
    RECOMMENDATION(R.string.tab_title_recommendation, R.drawable.recommendation, R.drawable.recommendation_ccolor),
    COMMUNITY(R.string.tab_title_community, R.drawable.community, R.drawable.community_ccolor),
    PRODUCT(R.string.tab_title_product, R.drawable.product, R.drawable.product_ccolor),
    MYPAGE(R.string.tab_title_mypage, R.drawable.mypage, R.drawable.mypage_ccolor)

}