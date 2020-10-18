package com.rollyglobe.ui.recommend.inner_contents

import androidx.lifecycle.MutableLiveData
import com.rollyglobe.AppComponents
import com.rollyglobe.R
import com.rollyglobe.network.RollyGlobeApiClient
import com.rollyglobe.network.model.SpotInnerContentsModel
import com.rollyglobe.network.model.request_model.*
import com.rollyglobe.network.model.response_model.LoadCommentListResponseModel
import com.rollyglobe.support.basemodel.BaseViewModel
import org.json.JSONObject
import org.koin.core.inject

class InnerContentsViewModel : BaseViewModel() {

    val restClient: RollyGlobeApiClient by inject()

    var spotDetail = MutableLiveData<SpotInnerContentsModel>()

    var spotComments = MutableLiveData<List<LoadCommentListResponseModel>>()

    fun getSpotInnerContents(spotNumber: Int) {

        val option = InnerContentsOption(spotNumber)
        val request = InnerContentsRequest("GetSpotInnerContents", option)
        val requestModel = InnerContentsRequestModel(request)

        disposable.add(restClient.getSpotInnerContents(requestModel).subscribe { result ->
            val str = result.string()
            val resultJson = JSONObject(str)

            spotDetail.value = SpotInnerContentsModel(
                resultJson,
                AppComponents.applicationContext.getString(R.string.lets_be_contributor)
            )
        })
    }

    fun getSpotComments(spotNumber: Int) {

        val loadCommentListOption = LoadCommentListOption(spotNumber, "spot")
        val loadCommentListRequest = LoadCommentListRequest(loadCommentListOption)
        val loadCommentListRequestModel = LoadCommentListRequestModel(loadCommentListRequest)

        disposable.add(
            restClient.loadCommentList(loadCommentListRequestModel).subscribe({
                spotComments.value = it
            }, {
                it.printStackTrace()
            })
        )

    }

}