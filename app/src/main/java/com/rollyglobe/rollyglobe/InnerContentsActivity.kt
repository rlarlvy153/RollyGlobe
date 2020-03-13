package com.rollyglobe.rollyglobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.rollyglobe.rollyglobe.Model.SpotInnderContentsModel
import com.rollyglobe.rollyglobe.Model.SpotModel
import com.rollyglobe.rollyglobe.Model.request_model.InnerContentsOption
import com.rollyglobe.rollyglobe.Model.request_model.InnerContentsRequest
import com.rollyglobe.rollyglobe.Model.request_model.InnerContentsRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_inner_contents.*
import org.json.JSONObject
import timber.log.Timber

class InnerContentsActivity : AppCompatActivity() {
    var restClient = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_contents)

        val spot : SpotModel = intent.getSerializableExtra("spotModel") as SpotModel
        Timber.d("${spot.spotCityName}")

        val option = InnerContentsOption(spot.spotNum)
        val request = InnerContentsRequest("GetSpotInnerContents", option)
        val requestModel = InnerContentsRequestModel(request)

        disposable.add(
            restClient.getSpotInnerContents(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val str = result.string()
                    val resultJson = JSONObject(str)
                    val resultSpot = SpotInnderContentsModel(resultJson)
                    initView(resultSpot)

                }, {

                })
        )


        Glide.with(this).load(spot.spotThumbnailPath).into(spot_image)
        spot_name.text = spot.spotTitleKor

    }

    fun initView(spot:SpotInnderContentsModel){
        spot_name.text = spot.spotTitleKor
        spot_name_eng.text = spot.spotTitleEng
        val concat = "${spot.spotContinent} - ${spot.spotNation} - ${spot.spotCity}"
        spot_position.text = concat
        spot_detail.text = spot.spotDetail
        spot_time.text = spot.spotTime
        spot_cost.text = spot.spotCost
        spot_address.text = spot.spotAddress
        spot_traffic.text = spot.spotTraffic
        spot_contact.text = spot.spotContact
        spot_web.text = spot.spotWeb

    }
}
