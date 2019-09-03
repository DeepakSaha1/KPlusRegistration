package com.example.kplusregistration.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kplusregistration.model.RegisterModel

class RegisterViewModel : ViewModel() {
    var mEmail: MutableLiveData<String> = MutableLiveData()
    var mPassword: MutableLiveData<String> = MutableLiveData()

    var registerUserMutableLiveData: MutableLiveData<RegisterModel> = MutableLiveData()

//    fun getUser(): MutableLiveData<RegisterModel> {
//
//        if (registerUserMutableLiveData == null) {
//            registerUserMutableLiveData = MutableLiveData()
//        }
//        return registerUserMutableLiveData
//    }

    fun onClick(v: View) {
        Log.d("step1", "register button clicked..")
        var registerModel = RegisterModel(mEmail.value?.trim()!!, mPassword.value?.trim()!!, "1234560", "9934676950")
        registerUserMutableLiveData.value = registerModel
        Log.d("Google", "${mEmail.value}")
    }
}