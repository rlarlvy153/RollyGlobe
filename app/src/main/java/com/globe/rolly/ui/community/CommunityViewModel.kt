package com.globe.rolly.ui.community

import androidx.lifecycle.MutableLiveData
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.request_model.GetPostListOption
import com.globe.rolly.network.model.request_model.GetPostListRequest
import com.globe.rolly.network.model.request_model.GetPostListRequestModel
import com.globe.rolly.network.model.response_model.Post
import com.globe.rolly.support.baseclass.BaseViewModel
import com.globe.rolly.support.extension.addTo
import org.koin.core.inject

class CommunityViewModel :BaseViewModel(){

    private val rollyGlobeApiClient : RollyGlobeApiClient by inject()

    val postList = MutableLiveData<List<Post>>()

    fun getPostList(){
        val option = GetPostListOption(10, 0)
        val request = GetPostListRequest(option = option)

        val requestModel = GetPostListRequestModel(request)

        rollyGlobeApiClient.getPostList(requestModel)
            .subscribe {

                for(info in it.postList.infos){
                    info.parseJson()
                }

                postList.value = it.postList.infos

            }.addTo(compositeDisposable)

    }
}
