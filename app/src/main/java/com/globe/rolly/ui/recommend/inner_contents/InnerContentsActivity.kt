package com.globe.rolly.ui.recommend.inner_contents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.globe.R
import com.globe.databinding.ActivityInnerContentsBinding
import com.globe.rolly.network.model.SpotInnerContentsResult
import com.globe.rolly.network.model.SpotModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class InnerContentsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val innerContentsViewModel: InnerContentsViewModel by viewModel()

    private lateinit var mapFragment: WorkaroundMapFragment

    private lateinit var googleMap: GoogleMap

    private lateinit var binding: ActivityInnerContentsBinding

    override fun onMapReady(map: GoogleMap) {

        googleMap = map.apply {
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
                binding.innerContentsScrollView.requestDisallowInterceptTouchEvent(true)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInnerContentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun setSpotProduct(spot: SpotInnerContentsResult) {
        val container = binding.spotProductContainer

//        for (obj in spot.spotProductJSONArrayList) {
//            val viewBinding = SpotProductItemBinding.inflate(layoutInflater, container, false)
//            val view = viewBinding.root
//
//            val imageURL = "m.rollyglobe.com/post/pic/${obj.productThumbnailNum}.${obj.productThumbType}"
//
//            Glide.with(this).load(imageURL).placeholder(ColorDrawable(Color.RED)).into(viewBinding.image)
//
//            viewBinding.title.text = obj.productName
//
//            viewBinding.intro.text = obj.productIntro
//
//            viewBinding.cost.text = obj.productCost
//
//            container.addView(view)
//        }
    }

    private fun setSpotInfo(spot: SpotInnerContentsResult) {
        //TODO 이미지 viewpager
//        if (spot.spotPicList.size > 0) {0
//
//        }
        Glide.with(this).load("https://m.rollyglobe.com/post/pics/small/" +"${spot.thumbnailInfo.regdate}/" + spot.thumbnailInfo.picName)
            .into(binding.spotImage)
        binding.spotName.text = spot.spotTitleKor
        binding.spotNameEng.text = spot.spotTitleEng

        val concat =
            "${spot.geoTypeInfo.country.geocodeTypeNameEngShort ?: ""} - ${spot.geoTypeInfo.adminLevelGeocode1.geocodeTypeNameEngShort ?: ""} - " +
             "${spot.geoTypeInfo.adminLevelGeocode2.geocodeTypeNameEngShort ?: ""} - ${spot.geoTypeInfo.locality.geocodeTypeNameEngShort ?: ""}"
        binding.spotPosition.text = concat
        binding.spotDetail.text = spot.spotDetail
        binding.spotTime.text = spot.spotTime
        binding.spotCost.text = spot.spotCost
        binding.spotAddress.text = spot.spotAddress
        binding.spotRoute.text = spot.spotTraffic
        binding.spotContact.text = spot.spotContact
        binding.spotWeb.text = spot.spotWeb
        binding.majorTag.text = "#${spot.spotMagorTag}"
        val tagListBuilder = StringBuilder()
//        for (tag in spot.tagList) {
//            tagListBuilder.append("  #$tag")
//        }
        binding.tagList.text = tagListBuilder.toString()
    }

    private fun initView(spot: SpotInnerContentsResult) {

        setSpotInfo(spot)

        setSpotProduct(spot)

        moveMapCamera(LatLng(spot.spotLat, spot.spotLng))

    }
}

