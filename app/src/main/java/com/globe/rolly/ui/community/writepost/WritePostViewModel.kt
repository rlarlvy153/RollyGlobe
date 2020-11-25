package com.globe.rolly.ui.community.writepost

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.globe.rolly.support.ImageFolder
import io.reactivex.Observable
import timber.log.Timber

class WritePostViewModel(val app: Application) : AndroidViewModel(app) {

    val imageHashMapLiveData = MutableLiveData<ArrayList<String>>()

    val imageHashMap = HashMap<String,ArrayList<String>>()

    fun loadImages() {
        Timber.d("kgp start loadImages")
        val picFolders: ArrayList<ImageFolder> = ArrayList()
        val picPaths: ArrayList<String> = ArrayList()

        val allImagesuri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID
        )
        val cursor = app.contentResolver.query(allImagesuri, projection, null, null, null)

        try {
            cursor?.moveToFirst()
            do {
                val folds = ImageFolder()
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))

                val folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                //String folderpaths =  datapath.replace(name,"");
                var folderpaths = datapath.substring(0, datapath.lastIndexOf("$folder/"))
                folderpaths = "$folderpaths$folder/"
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths)
                    folds.path = folderpaths
                    folds.folderName = folder
                    folds.firstPic = datapath //if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics(name)
                    picFolders.add(folds)
                } else {
                    for (i in 0 until picFolders.size) {
                        if (picFolders.get(i).path.equals(folderpaths)) {
                            picFolders.get(i).firstPic = datapath
                            picFolders.get(i).addpics(name)
                        }
                    }
                }

            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        for (i in 0 until picFolders.size) {
//            for (j in 0 until picFolders.get(i).images.size){
//                Timber.d("and path = " + picFolders.get(i).getPath() + " " + picFolders.get(i).images[j])
//            }
//        }
//        imageFolders.value = picFolders

    }

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
//                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))

                val folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))


//                Timber.d("name : $name")
//                Timber.d("folder : $folder")
//                Timber.d("datapath : $datapath")

                if(imageHashMap[folder] == null){
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