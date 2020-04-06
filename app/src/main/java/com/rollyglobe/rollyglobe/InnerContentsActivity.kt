package com.rollyglobe.rollyglobe

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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

class InnerContentsActivity : AppCompatActivity(), OnMapReadyCallback {
    var restClient = RetrofitCreator.getRetrofitService(RollyGlobeApiInterface::class.java)
    private val disposable = CompositeDisposable()
    lateinit var mapFragment:SupportMapFragment
    lateinit var googleMap:GoogleMap
    lateinit var resultSpot : SpotInnderContentsModel
    override fun onMapReady(map : GoogleMap) {

        googleMap = map
        googleMap.run{
            uiSettings.isZoomControlsEnabled = true
//            setOnMarkerClickListener(this@MainActivity)
            //googleMap.setPadding(left, top, right, bottom);

        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_contents)

        supportActionBar?.hide()
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


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
                    Timber.d(str.substring(0,str.length/2))
                    Timber.d(str.substring(str.length/2))
                    resultSpot = SpotInnderContentsModel(resultJson, resources.getString(R.string.lets_be_contributor))
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
        spot_route.text = spot.spotTraffic
        spot_contact.text = spot.spotContact
        spot_web.text = spot.spotWeb
        major_tag.text = "#${spot.spotMajorTag}"
        val tagListBuilder = StringBuilder()
        for(tag in spot.tagList){
            tagListBuilder.append("  #$tag")
        }
        tag_list.text = tagListBuilder.toString()

        val position = LatLng(resultSpot.spotLat, resultSpot.spotLong)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17f))
        googleMap.addMarker(
            MarkerOptions().position(position)
        )

        val container = spot_product_container

        for( obj in  spot.spotProductJSONArrayList){
            Timber.d(obj.productName)
            val view = layoutInflater.inflate(R.layout.spot_product_item,null)
            val imageURL = "https://rollyglobe.com/_product/_thumbnail/${obj.productThumbnailNum}.${obj.productThumbType}"
            Timber.d("res " + imageURL)
            val imageView = view.findViewById<ImageView>(R.id.image)

            Glide.with(this).load(imageURL).placeholder(
                ColorDrawable(Color.RED)).into(imageView)

            view.findViewById<TextView>(R.id.title).setText(obj.productName)
            view.findViewById<TextView>(R.id.intro).setText(obj.productIntro)
            view.findViewById<TextView>(R.id.cost).setText(obj.productCost)
            container.addView(view)
        }

    }



}

