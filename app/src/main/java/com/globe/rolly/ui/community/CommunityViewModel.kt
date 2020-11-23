package com.globe.rolly.ui.community

import androidx.lifecycle.MutableLiveData
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.request_model.GetPostListOption
import com.globe.rolly.network.model.request_model.GetPostListRequest
import com.globe.rolly.network.model.request_model.GetPostListRequestModel
import com.globe.rolly.network.model.response_model.PostInfo
import com.globe.rolly.support.baseclass.BaseViewModel
import com.globe.rolly.support.extension.addTo
import org.koin.core.inject

class CommunityViewModel :BaseViewModel(){

    private val rollyGlobeApiClient : RollyGlobeApiClient by inject()

    val postList = MutableLiveData<List<PostInfo>>()

    fun getPostList(){
        val option = GetPostListOption(10, 0)
        val request = GetPostListRequest(option = option)

        val requestModel = GetPostListRequestModel(request)

        rollyGlobeApiClient.getPostList(requestModel)
            .subscribe {

                for(post in it.postList.postInfo){
                    post.parseJson()
                }

                postList.value = it.postList.postInfo

            }.addTo(compositeDisposable)

    }
}
