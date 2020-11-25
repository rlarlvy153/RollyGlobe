package com.globe.rolly.support.di


import com.globe.rolly.ui.MainViewModel
import com.globe.rolly.ui.community.CommunityViewModel
import com.globe.rolly.ui.community.writepost.WritePostViewModel
import com.globe.rolly.ui.recommend.inner_contents.InnerContentsViewModel
import com.globe.rolly.ui.signin.SignInViewModel
import com.globe.rolly.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { InnerContentsViewModel() }
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { CommunityViewModel() }
    viewModel { WritePostViewModel(get()) }
}