package com.globe.rolly.ui.signin

import androidx.lifecycle.MutableLiveData
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.user.signin.SignInOption
import com.globe.rolly.network.model.user.signin.SignInRequest
import com.globe.rolly.network.model.user.signin.SignInRequestModel
import com.globe.rolly.support.baseclass.BaseViewModel
import org.koin.core.inject
import timber.log.Timber

class SignInViewModel : BaseViewModel(){

    val restClient: RollyGlobeApiClient by inject()

    val successSignIn = MutableLiveData<Boolean>()

    val showErrorMsg = MutableLiveData<String>()

    fun signIn(emailAddress:String, pw:String, auto:Boolean){
        val option  = SignInOption("email", emailAddress, pw, auto)
        val signInRequest = SignInRequest("SignIn", option)
        val signInRequestModel = SignInRequestModel(signInRequest)

        compositeDisposable.add(restClient.signIn(signInRequestModel)
            .subscribe({result->
                if(!result.success){
                    showErrorMsg.value = result.msg
                    showErrorMsg.value = ""

                    return@subscribe
                }

                Timber.d(result.toString())
                Timber.d("${result?.user_info?.user_birthday}")

                successSignIn.value = true
            },{

            })
        )
    }

}