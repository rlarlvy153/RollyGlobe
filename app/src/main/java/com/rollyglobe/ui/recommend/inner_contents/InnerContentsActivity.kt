package com.rollyglobe.ui.recommend.inner_contents

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rollyglobe.R
import com.rollyglobe.network.model.SpotInnerContentsModel
import com.rollyglobe.network.model.SpotModel
import kotlinx.android.synthetic.main.activity_inner_contents.*
import kotlinx.android.synthetic.main.spot_product_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InnerContentsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val innerContentsViewModel: InnerContentsViewModel by viewModel()

    private lateinit var mapFragment: WorkaroundMapFragment

    private lateinit var googleMap: GoogleMap

    override fun onMapReady(map: GoogleMap) {

        googleMap = map.apply{
            uiSettings.isZoomControlsEnabled = true
        }

        val spot: SpotModel = intent.getSerializableExtra("spotModel") as SpotModel

        innerContentsViewModel.getSpotInnerContents(spot.spotNum)

        innerContentsViewModel.getSpotComments(spot.spotNum)
    }

    private fun initMapFragment() {

        mapFragment = (supportFragmentManager.findFragmentById(R.id.fragmentMap) as WorkaroundMapFragment)

        mapFragment.getMapAsync(this)

        mapFragment.setListener(object : WorkaroundMapFragment.OnTouchListener {
            override fun onTouch() {
                inner_contents_scroll_view.requestDisallowInterceptTouchEvent(true)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_contents)

        supportActionBar?.hide()

        initMapFragment()

        observeEvent()
    }

    private fun observeEvent() {
        innerContentsViewModel.spotDetail.observe(this, Observer {
            initView(it)
        })

        //TODO spot comments observe

    }

    private fun moveMapCamera(position: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17f))
        googleMap.addMarker(
            MarkerOptions().position(position)
        )
    }

    private fun setSpotProduct(spot: SpotInnerContentsModel) {
        val container = spotProductContainer

        for (obj in spot.spotProductJSONArrayList) {
            val view = layoutInflater.inflate(R.layout.spot_product_item, container, false)

            val imageURL = "m.rollyglobe.com/post/pic/${obj.productThumbnailNum}.${obj.productThumbType}"

            Glide.with(this).load(imageURL).placeholder(ColorDrawable(Color.RED)).into(view.image)

            view.title.text = obj.productName

            view.intro.text = obj.productIntro

            view.cost.text = obj.productCost

            container.addView(view)
        }
    }

    private fun setSpotInfo(spot : SpotInnerContentsModel){
        //TODO 이미지 viewpager
        if (spot.spotPicList.size > 0) {
            Glide.with(this).load("https://m.rollyglobe.com/post/pics/small/" + spot.spotPicList[0])
                .into(spotImage)
        }

        spotName.text = spot.spotTitleKor
        spotNameEng.text = spot.spotTitleEng
        val concat = "${spot.spotContinent} - ${spot.spotNation} - ${spot.spotCity}"
        spotPosition.text = concat
        spotDetail.text = spot.spotDetail
        spotTime.text = spot.spotTime
        spotCost.text = spot.spotCost
        spotAddress.text = spot.spotAddress
        spotRoute.text = spot.spotTraffic
        spotContact.text = spot.spotContact
        spotWeb.text = spot.spotWeb
        majorTag.text = "#${spot.spotMajorTag}"
        val tagListBuilder = StringBuilder()
        for (tag in spot.tagList) {
            tagListBuilder.append("  #$tag")
        }
        tagList.text = tagListBuilder.toString()
    }

    private fun initView(spot: SpotInnerContentsModel) {

        setSpotInfo(spot)

        setSpotProduct(spot)

        moveMapCamera(LatLng(spot.spotLat, spot.spotLong))

    }
}
