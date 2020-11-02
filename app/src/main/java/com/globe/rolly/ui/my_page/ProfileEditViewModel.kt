package com.globe.rolly.ui.my_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileEditViewModel : ViewModel(){

    val EDIT_NAME="name"
    val EDIT_PHONENUMBER="phone_number"
    val EDIT_EMAIL="email"
    val EDIT_BIRTHDAY="birthday"
    val EDIT_GENDER="gender"

    var userName = MutableLiveData<String>()
    var userPhoneNumber = MutableLiveData<String>()
    var userEmail = MutableLiveData<String>()
    var userBirthday = MutableLiveData<String>()
    var userGender = MutableLiveData<String>()
    var userNationCode = 0
    fun setName(name:String){
        userName.value = name
    }
    fun setPhoneNumber(phone:String){
        userPhoneNumber.value = phone
    }
    fun setEmail(email:String){
        userEmail.value = email
    }
    fun setBirthday(birth:String){
        userBirthday.value = birth
    }
    fun setGender(gender:String){
        userGender.value=gender
    }
}