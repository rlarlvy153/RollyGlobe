package com.globe.rolly.network.model

import com.google.gson.annotations.SerializedName

data class GeocodeInfo(
    @SerializedName("geocode_type_name_en")
    val geocodeTypeNameEng: String?,

    @SerializedName("geocode_type_name_en_short")
    val geocodeTypeNameEngShort: String,

    @SerializedName("geocode_type_name_ko")
    val geocodeTypeNameKo: String?,
)

data class GeotypeInfo(

    @SerializedName("country")
    val country: GeocodeInfo,

    @SerializedName("administrative_area_level_1")
    val adminLevelGeocode1: GeocodeInfo,

    @SerializedName("administrative_area_level_2")
    val adminLevelGeocode2: GeocodeInfo,

    @SerializedName("locality")
    val locality: GeocodeInfo
)

data class ThumbnailInfo(
    @SerializedName("post_pic_name")
    val picName: String,

    @SerializedName("post_pic_type")
    val picType: String,

    @SerializedName("post_pic_regdate")
    val regdate: String,
)

data class SpotInnerContentsResult(
    @SerializedName("spot_num")
    val spotNum: Int,

    @SerializedName("spot_title_kor")
    val spotTitleKor: String,

    @SerializedName("spot_title_eng")
    val spotTitleEng: String,

    @SerializedName("spot_title_native")
    val spotTitleNative: String,

    @SerializedName("spot_geocode")
    val spotGeocode: String,

    @SerializedName("spot_country")
    val spotCountry: Int,

    @SerializedName("spot_administrative_area_level_1")
    val spotAdministrativeAreaLevel1: Int,

    @SerializedName("spot_administrative_area_level_2")
    val spotAdministrativeAreaLevel2: Int,

    @SerializedName("spot_locality")
    val spotLocality: Int,

    @SerializedName("spot_major_tag")
    val spotMagorTag: Int,

    @SerializedName("spot_intro")
    val spotIntro: String,

    @SerializedName("spot_detail")
    val spotDetail: String,

    @SerializedName("spot_time")
    val spotTime: String,

    @SerializedName("spot_cost")
    val spotCost: String,

    @SerializedName("spot_contact")
    val spotContact: String,

    @SerializedName("spot_address")
    val spotAddress: String,

    @SerializedName("spot_traffic")
    val spotTraffic: String,

    @SerializedName("spot_web")
    val spotWeb: String,

    @SerializedName("spot_lat")
    val spotLat: Double,

    @SerializedName("spot_long")
    val spotLng: Double,

    @SerializedName("spot_user")
    val spotUser: Int,

    @SerializedName("spot_regdate")
    val spotRegdate: String,

    @SerializedName("spot_score")
    val spotSCore: Int,

    @SerializedName("geocode_type_info")
    val geoTypeInfo: GeotypeInfo,

    @SerializedName("spot_thumbnail_info")
    val thumbnailInfo: ThumbnailInfo
)

data class SpotInnerContentsResponseModel(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("result")
    val result: SpotInnerContentsResult,

    @SerializedName("message")
    val message: String

)