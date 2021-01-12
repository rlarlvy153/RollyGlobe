package com.globe.rolly.ui.recommend.inner_contents

import androidx.lifecycle.MutableLiveData
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.SpotInnerContentsResult
import com.globe.rolly.network.model.request_model.*
import com.globe.rolly.network.model.response_model.LoadCommentListResponseModel
import com.globe.rolly.support.baseclass.BaseViewModel
import org.koin.core.inject

class InnerContentsViewModel : BaseViewModel() {

    val restClient: RollyGlobeApiClient by inject()

    var spotDetail = MutableLiveData<SpotInnerContentsResult>()

    var spotComments = MutableLiveData<List<LoadCommentListResponseModel>>()

    fun getSpotInnerContents(spotNumber: Int) {

        val option = InnerContentsOption(spotNumber)
        val request = InnerContentsRequest("GetSpotInnerContents", option)
        val requestModel = InnerContentsRequestModel(request)

        compositeDisposable.add(restClient.getSpotInnerContents(requestModel).subscribe { result ->

            spotDetail.value = result.result
        })
    }

    fun getSpotComments(spotNumber: Int) {

        val loadCommentListOption = LoadCommentListOption(spotNumber, "spot")
        val loadCommentListRequest = LoadCommentListRequest(loadCommentListOption)
        val loadCommentListRequestModel = LoadCommentListRequestModel(loadCommentListRequest)

        compositeDisposable.add(
            restClient.loadCommentList(loadCommentListRequestModel).subscribe({
                spotComments.value = it
            }, {
                it.printStackTrace()
            })
        )

    }

}