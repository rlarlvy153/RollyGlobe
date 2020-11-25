package com.globe.rolly.support

class ImageFolder(){
    var path: String? = null
    var folderName: String? = null
    var numberOfPics = 0
    var firstPic: String? = null

    var images = ArrayList<String>()
    fun imageFolder() {}

    fun imageFolder(path: String?, folderName: String?) {
        this.path = path
        this.folderName = folderName
    }

    fun addpics(uri:String) {
        numberOfPics++
        images.add(path + '/' + uri)
    }

}