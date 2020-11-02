package com.globe.rolly.support.basemodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

open class BaseViewModel: ViewModel() , KoinComponent{
    val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()

        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}