package com.globe.rolly.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.globe.rolly.network.RollyGlobeApiClient
import com.globe.rolly.network.model.SpotModel
import com.globe.rolly.network.model.spot.geocode.*
import com.globe.rolly.network.model.request_model.*
import com.globe.rolly.network.model.response_model.ReservationModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class MainViewModel : ViewModel(), KoinComponent {
    val gson = Gson()

    //    var restClient  = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
    val restClient: RollyGlobeApiClient by inject()
    var spotList = ArrayList<SpotModel>()
    var spotListLiveData = MutableLiveData<ArrayList<SpotModel>>()
    val logouted = MutableLiveData<Boolean>()

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

    val showErrorMsg = MutableLiveData<String>()

    init {
        Timber.d("mainViewModel init")
        logouted.value = false
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
                .subscribe({ result ->


//                    val str = result.string()
//                    Timber.d("hello : $str")
//                var f = File("${Environment.getExternalStorageDirectory()}/hello.txt")
//                PrintWriter(f).use { out -> out.println(str)}

//                PrintWriter(f).use { out -> out.println("dddddddddddd") }
//                    val resultJson = JSONObject(str)
//                    val spotCnt = resultJson.getInt("spot_list_total_cnt")


//                    Timber.d("cnt : $spotCnt")
//                    for (cnt in 0 until spotCnt) {
//                        val spotNum = resultJson.getInt("spot_num$cnt")
//                        val spotThumbnailNum = resultJson.getInt("spot_thumbnail_num$cnt")
//                        val spotThumbnailType = resultJson.getString("spot_thumbnail_type$cnt")
//                        val spotTitleKor = resultJson.getString("spot_title_kor$cnt")
//
////                    val continentList = resultJson.getInt("spot_continent$cnt")
//                        val continentName =
//                            resultJson.getString("city_list${resultJson.getString("spot_continent$cnt")}")
//                        val nationName =
//                            resultJson.getString("city_list${resultJson.getInt("spot_nation$cnt")}")
//                        val cityName =
//                            resultJson.getString("city_list${resultJson.getInt("spot_city$cnt")}")
//                        val spotSimpleIntro = resultJson.getString("spot_intro$cnt")
//                        val recommendationReason =
//                            resultJson.getString("spot_recommenation_reason$cnt")
//
////                    Timber.d("spotNum : $spotNum")
////                    Timber.d("spotThumbnailNum : $spotThumbnailNum")
////                    Timber.d("spotThumbnailType : $spotThumbnailType")
////                    Timber.d("spotTitleKor : $spotTitleKor")
////
////                    Timber.d("continentNum : $continentName")
////                    Timber.d("nationNum : $nationName")
////                    Timber.d("cityNum : $cityName")
////                    Timber.d("spotSimpleIntro : $spotSimpleIntro")
////                    Timber.d("spotSimpleIntro : $spotSimpleIntro")
////                    Timber.d("recommendationReason : $recommendationReason")
//
////                        val spot = SpotModel(
////                            spotNum,
////                            spotThumbnailNum,
////                            spotThumbnailType,
////                            spotTitleKor,
////                            continentName,
////                            nationName,
////                            cityName,
////                            spotSimpleIntro,
////                            recommendationReason
////                        )
////                        spotList.add(spot)
//                    }
                    spotList.clear()
                    spotList.addAll(result.spotList)
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
                .subscribe({ result ->

                }, {

                })
        )
    }

    fun getMyPageHome() {
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

        disposable.add(
            restClient.getMyPageHomeInfo(myPageHomeRequestModel)
                .subscribe({ result ->
                    if(!result.success){
                        showErrorMsg.value = result.msg
                        showErrorMsg.value = ""

                        return@subscribe
                    }
//                myInfoDummy.value += "${result.success}\n"
//                myInfoDummy.value += "${result.userEmail}\n"
//                myInfoDummy.value += "${result.userNickname}\n"
//                myInfoDummy.value += "${result.userPhoneNum}\n"
//                myInfoDummy.value += "${result.userSex}\n"

                    follower.value = 0
                    following.value = 0
                    userName.value = result.user.userNickname
                    userEmail.value = result.user.userEmail
                    userPhoneNumber.value = result.user.userPhoneNum
                    userSex.value = result.user.userSex
                    userBirtday.value = result.user.userBirthday
                    userNationCode = result.user.userNationCode.toInt()

//                val reservationJsonArray = JSONArray(result.reservationInfoList)
//                reservations.value?.clear()
//                for( i in 0 until reservationJsonArray.length()){
//                    val eachObject = reservationJsonArray.getJSONObject(i)
//
//                    val reservationNum = eachObject.getString("reservationNum").toInt()
//                    val reservationName = eachObject.getString("reservationName")
//                    val reservationIntro = eachObject.getString("reservationIntro")
//                    val reservationProductNum = eachObject.getString("reservationProductNum").toInt()
//                    val reservationThumbnailNum = eachObject.getString("reservationThumbnailNum").toInt()
//                    val reservationThumbnailType = eachObject.getString("reservationThumbnailType")
//                    val tempReservation = ReservationModel(reservationNum, reservationProductNum, reservationName, reservationIntro, reservationThumbnailNum, reservationThumbnailType)
//                    reservations.value?.add(tempReservation)
//
//                }
//                reservations.value = reservations.value

                }, {
                    Timber.d("err : ${it.toString()}")

                })
        )
    }

    fun logout() {
        userName.value = ""
        userEmail.value = ""
        userPhoneNumber.value = ""
        userSex.value = ""
        userBirtday.value = ""
        reservations.value?.clear()
        logouted.value = true
    }

    fun getGeocodeByGps() {
        Timber.d("call gps")
        val req = GeocodeByGpsRequestModel()
        req.request = GeocodeByGpsRequest(
            "GetGeocodeByGps",
            GpsOption(37.5038022f, 127.0242523f)
        )
//        req.request.funcName = "GetGeocodeByGps"
//        req.request.option = GpsOption(37.5038022f, 127.0242523f)
        disposable.add(restClient.getGeocodeByGps(req).subscribe( {
            Timber.d("ggggggggggg" + it.geocode)
        }))


    }
    fun getGeocodeByPlaceId(){
        val req = GeocodeByPlaceIdRequestModel()
        req.request = GeocodeByPlaceIdRequest("GetGeocodeByPlaceId", PlaceIdOption("ChIJNwUpIdSjfDURprYWgSEWUgs"))
        disposable.add(restClient.getGeocodeByPlaceId(req).subscribe {
            Timber.d("aaaaaaaaaa" + it.success)
            Timber.d("aaaaaaaaaa" + it.msg)
            Timber.d("aaaaaaaaaa" + it.geocode)
        })
    }

}