package com.globe.rolly.support.baseclass

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

open class BaseViewModel: ViewModel() , KoinComponent{
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()

        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}