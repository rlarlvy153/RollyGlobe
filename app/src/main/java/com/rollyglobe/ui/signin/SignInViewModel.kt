package com.rollyglobe.ui.signin

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.rollyglobe.network.RollyGlobeApiClient
import com.rollyglobe.network.model.user.signin.SignInOption
import com.rollyglobe.network.model.user.signin.SignInRequest
import com.rollyglobe.network.model.user.signin.SignInRequestModel
import com.rollyglobe.support.Utils
import com.rollyglobe.support.basemodel.BaseViewModel
import com.rollyglobe.ui.MainActivity
import org.koin.android.ext.android.inject
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

        disposable.add(restClient.signIn(signInRequestModel)
            .subscribe({result->
                if(!result.success){
                    showErrorMsg.value = result.msg
                    showErrorMsg.value = ""

                    return@subscribe
                }

                Timber.d(result.toString())
                Timber.d("${result?.user_info?.user_birthday}")

            },{

            })
        )
    }

}