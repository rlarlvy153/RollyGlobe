package com.rollyglobe.ui.signup

import androidx.lifecycle.MutableLiveData
import com.rollyglobe.network.RollyGlobeApiClient
import com.rollyglobe.network.model.user.signup.SignUpOption
import com.rollyglobe.network.model.user.signup.SignUpRequest
import com.rollyglobe.network.model.user.signup.SignUpRequestModel
import com.rollyglobe.support.basemodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.inject
import timber.log.Timber

class SignUpViewModel : BaseViewModel() {

    val restClient: RollyGlobeApiClient by inject()

    val nationCodeString = MutableLiveData<ArrayList<String>>()

    val signUpResult = MutableLiveData<Boolean>()

    val signUpErrorMsg = MutableLiveData<String>()

    fun callNationCode() {
        disposable.add(restClient.getNationCodeInfoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val codeStringList = ArrayList<String>()

                for (code in result) {
                    codeStringList.add(code.toString())
                }

                nationCodeString.value = codeStringList

            }, {
                Timber.d("error")
            }

            )
        )
    }

    fun signUp(
        email: String, nickname: String, pw: String, nation: String, contact: String,
        gender: String, y: String, m: String, d: String
    ) {

        val option = SignUpOption(
            email,
            nickname,
            pw,
            nation,
            contact,
            gender,
            y,
            if (m.toInt() < 10) "0$m" else m,
            if (d.toInt() < 10) "0$d" else d
        )

        val signUpRequestModel = SignUpRequestModel()
        signUpRequestModel.request = SignUpRequest("SignUp", option)

        disposable.add(restClient.signUp(signUpRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if(!result.success){
                    signUpErrorMsg.value = result.msg
                    signUpErrorMsg.value = ""
                    return@subscribe
                }
                signUpResult.value = true

            },
                {

                }
            )
        )
    }
}