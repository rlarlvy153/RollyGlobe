package com.rollyglobe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.rollyglobe.network.model.SpotModel
import com.rollyglobe.network.model.request_model.*
import com.rollyglobe.network.model.request_model.MyPageHomeRequest
import com.rollyglobe.network.model.request_model.MyPageHomeRequestModel
import com.rollyglobe.network.model.response_model.ReservationModel
import com.rollyglobe.network.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class MainViewModel : ViewModel() {
    val gson = Gson()

//    var restClient  = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
    var restClient = RestClient.restClient
    var spotList = ArrayList<SpotModel>()
    var spotListLiveData = MutableLiveData<ArrayList<SpotModel>>()
    var isLogin = MutableLiveData<Boolean>()
//    var myInfoDummy = MutableLiveData<String>()
    var userName = MutableLiveData<String>()
    var userEmail = MutableLiveData<String>()
    var userPhoneNumber = MutableLiveData<String>()
    var userSex = MutableLiveData<String>()
    var userBirtday = MutableLiveData<String>()
    var userNationCode = 0
    var reservations = MutableLiveData<ArrayList<ReservationModel>>()
    var follower = MutableLiveData<Int>()
    var following = MutableLiveData<Int>()
    private val disposable = CompositeDisposable()

    init {
        isLogin.value = false

//        myInfoDummy.value=""
        reservations.value = ArrayList<ReservationModel>()
    }

    fun getSpotList() {

        var tagBtnList = ArrayList<Int>()
        for (i in 1..11) {
            tagBtnList.add(0)
        }
        var recommendOption = RecommendOption(tagBtnList, "", "", "", "", "")
        var recommendRequest = RecommendRequest("GetSpotList", recommendOption)
        var recommendRequestModel = RecommendRequestModel(recommendRequest)

        disposable.add(
            restClient.getRecommendList(recommendRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val str = result.string()
                    Timber.d("hello : $str")
//                var f = File("${Environment.getExternalStorageDirectory()}/hello.txt")
//                PrintWriter(f).use { out -> out.println(str)}

//                PrintWriter(f).use { out -> out.println("dddddddddddd") }
                    val resultJson = JSONObject(str)
                    val spotCnt = resultJson.getInt("spot_cnt")


                    Timber.d("cnt : $spotCnt")
                    for (cnt in 0 until spotCnt) {
                        val spotNum = resultJson.getInt("spot_num$cnt")
                        val spotThumbnailNum = resultJson.getInt("spot_thumbnail_num$cnt")
                        val spotThumbnailType = resultJson.getString("spot_thumbnail_type$cnt")
                        val spotTitleKor = resultJson.getString("spot_title_kor$cnt")

//                    val continentList = resultJson.getInt("spot_continent$cnt")
                        val continentName =
                            resultJson.getString("city_list${resultJson.getString("spot_continent$cnt")}")
                        val nationName =
                            resultJson.getString("city_list${resultJson.getInt("spot_nation$cnt")}")
                        val cityName =
                            resultJson.getString("city_list${resultJson.getInt("spot_city$cnt")}")
                        val spotSimpleIntro = resultJson.getString("spot_intro$cnt")
                        val recommendationReason =
                            resultJson.getString("spot_recommenation_reason$cnt")

//                    Timber.d("spotNum : $spotNum")
//                    Timber.d("spotThumbnailNum : $spotThumbnailNum")
//                    Timber.d("spotThumbnailType : $spotThumbnailType")
//                    Timber.d("spotTitleKor : $spotTitleKor")
//
//                    Timber.d("continentNum : $continentName")
//                    Timber.d("nationNum : $nationName")
//                    Timber.d("cityNum : $cityName")
//                    Timber.d("spotSimpleIntro : $spotSimpleIntro")
//                    Timber.d("spotSimpleIntro : $spotSimpleIntro")
//                    Timber.d("recommendationReason : $recommendationReason")

                        val spot = SpotModel(
                            spotNum,
                            spotThumbnailNum,
                            spotThumbnailType,
                            spotTitleKor,
                            continentName,
                            nationName,
                            cityName,
                            spotSimpleIntro,
                            recommendationReason
                        )
                        spotList.add(spot)
                    }
                    spotListLiveData.value = spotList
//                Timber.d("cnt : ${resultJson.getString("spot_num0")}")


                }, {
                    Timber.d("err : ${it.toString()}")

                })
        )
    }

    fun getInnerContents(spotNum: Int) {
        val option = InnerContentsOption(spotNum)
        val request = InnerContentsRequest("GetSpotInnerContents", option)
        val requestModel = InnerContentsRequestModel(request)

        disposable.add(
            restClient.getSpotInnerContents(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->

                }, {

                })
        )
    }
    fun getMyPageHome(){
//        myInfoDummy.value=""
        val myPageHomeRequest =
            MyPageHomeRequest(
                "MypageHomeLoad",
                ""
            )
        val myPageHomeRequestModel =
            MyPageHomeRequestModel(
                myPageHomeRequest
            )



        disposable.add(restClient.getMyPageHomeInfo(myPageHomeRequestModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result->
//                myInfoDummy.value += "${result.success}\n"
//                myInfoDummy.value += "${result.userEmail}\n"
//                myInfoDummy.value += "${result.userNickname}\n"
//                myInfoDummy.value += "${result.userPhoneNum}\n"
//                myInfoDummy.value += "${result.userSex}\n"
                follower.value = 0
                following.value = 0
                userName.value = result.userNickname
                userEmail.value = result.userEmail
                userPhoneNumber.value = result.userPhoneNum
                userSex.value = result.userSex
                userBirtday.value = result.userBirthday
                userNationCode = result.userNationCode.toInt()

                val reservationJsonArray = JSONArray(result.reservationInfoList)
                reservations.value?.clear()
                for( i in 0 until reservationJsonArray.length()){
                    val eachObject = reservationJsonArray.getJSONObject(i)

                    val reservationNum = eachObject.getString("reservationNum").toInt()
                    val reservationName = eachObject.getString("reservationName")
                    val reservationIntro = eachObject.getString("reservationIntro")
                    val reservationProductNum = eachObject.getString("reservationProductNum").toInt()
                    val reservationThumbnailNum = eachObject.getString("reservationThumbnailNum").toInt()
                    val reservationThumbnailType = eachObject.getString("reservationThumbnailType")
                    val tempReservation = ReservationModel(reservationNum, reservationProductNum, reservationName, reservationIntro, reservationThumbnailNum, reservationThumbnailType)
                    reservations.value?.add(tempReservation)

                }
                reservations.value = reservations.value

            },{
                Timber.d("err : ${it.toString()}")

            })
        )
    }
    fun logout(){
        userName.value = ""
        userEmail.value = ""
        userPhoneNumber.value = ""
        userSex.value = ""
        userBirtday.value=""
        reservations.value?.clear()
        isLogin.value = false
    }
}