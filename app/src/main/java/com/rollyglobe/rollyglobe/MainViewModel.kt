package com.rollyglobe.rollyglobe

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.rollyglobe.rollyglobe.request_model.RecommendOption
import com.rollyglobe.rollyglobe.request_model.RecommendRequest
import com.rollyglobe.rollyglobe.request_model.RecommendRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import java.io.PrintWriter

class MainViewModel : ViewModel(){
    val gson = Gson()
    var restClient  = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
    var spotList = ArrayList<SpotModel>()
    var spotListLiveData = MutableLiveData<ArrayList<SpotModel>>()
    private val disposable = CompositeDisposable()

    fun getSpotList(){

        var tagBtnList = ArrayList<Int>()
        for (i in 1..11){
            tagBtnList.add(0)
        }
        var recommendOption = RecommendOption(tagBtnList,"","","","","")
        var recommendRequest = RecommendRequest("GetSpotList", recommendOption)
        var recommendRequestModel = RecommendRequestModel(recommendRequest)

        disposable.add(restClient.getRecommendList(recommendRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result->
                val str = result.string()
                Timber.d("hello : $str")
//                var f = File("${Environment.getExternalStorageDirectory()}/hello.txt")
//                PrintWriter(f).use { out -> out.println(str)}

//                PrintWriter(f).use { out -> out.println("dddddddddddd") }
                val resultJson = JSONObject(str)
                val spotCnt = resultJson.getInt("spot_cnt")


                Timber.d("cnt : $spotCnt")
                for(cnt in 0 until spotCnt){
                    val spotNum = resultJson.getInt("spot_num$cnt")
                    val spotThumbnailNum = resultJson.getInt("spot_thumbnail_num$cnt")
                    val spotThumbnailType = resultJson.getString("spot_thumbnail_type$cnt")
                    val spotTitleKor = resultJson.getString("spot_title_kor$cnt")

//                    val continentList = resultJson.getInt("spot_continent$cnt")
                    val continentName = resultJson.getString("city_list${resultJson.getString("spot_continent$cnt")}")
                    val nationName = resultJson.getString("city_list${resultJson.getInt("spot_nation$cnt")}")
                    val cityName = resultJson.getString("city_list${resultJson.getInt("spot_city$cnt")}")
                    val spotSimpleIntro = resultJson.getString("spot_intro$cnt")
                    val recommendationReason = resultJson.getString("spot_recommenation_reason$cnt")

                    Timber.d("spotNum : $spotNum")
                    Timber.d("spotThumbnailNum : $spotThumbnailNum")
                    Timber.d("spotThumbnailType : $spotThumbnailType")
                    Timber.d("spotTitleKor : $spotTitleKor")

                    Timber.d("continentNum : $continentName")
                    Timber.d("nationNum : $nationName")
                    Timber.d("cityNum : $cityName")
                    Timber.d("spotSimpleIntro : $spotSimpleIntro")
                    Timber.d("spotSimpleIntro : $spotSimpleIntro")
                    Timber.d("recommendationReason : $recommendationReason")

                    val spot = SpotModel(spotNum, spotThumbnailNum, spotThumbnailType,spotTitleKor,continentName,nationName,cityName,spotSimpleIntro,recommendationReason)
                    spotList.add(spot)
                }
                spotListLiveData.value = spotList
//                Timber.d("cnt : ${resultJson.getString("spot_num0")}")


            },{
                Timber.d("err : ${it.toString()}")

            })
        )
    }
}