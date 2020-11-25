package com.globe.rolly.ui.community.writepost

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class WritePostViewModel(val app: Application) : AndroidViewModel(app) {

    val imageHashMapLiveData = MutableLiveData<ArrayList<String>>()

    private val imageHashMap = HashMap<String, ArrayList<String>>()

    fun loadImagesByUri() {

        imageHashMap["전체보기"] = ArrayList<String>()
        val allImagesuri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID
        )
        val cursor = app.contentResolver.query(allImagesuri, projection, null, null, null)

        try {
            cursor?.moveToFirst()
            do {
                val folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                if (imageHashMap[folder] == null) {
                    imageHashMap[folder] = ArrayList<String>()
                }
                imageHashMap[folder]!!.add(datapath)
                imageHashMap["전체보기"]!!.add(datapath)

            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        imageHashMapLiveData.value = imageHashMap["전체보기"]
    }
}