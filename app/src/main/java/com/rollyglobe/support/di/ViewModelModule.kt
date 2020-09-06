package com.rollyglobe.support.di


import com.rollyglobe.ui.MainViewModel
import com.rollyglobe.ui.recommend.inner_contents.InnerContentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { InnerContentsViewModel() }
}