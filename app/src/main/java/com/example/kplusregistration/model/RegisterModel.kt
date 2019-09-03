package com.example.kplusregistration.model

import android.util.Patterns

data class RegisterModel(
    var _mEmail: String,
    var _mPassword: String,
    var _mConfirmPassword: String,
    var _mMobilePhone: String,
    var _mKPlusSmartCard: String = "",
    var _mKPlusSetUpBox: String = ""
) {

    private val mEmail: String = _mEmail
    private val mPassword: String = _mPassword
    private val mConfirmPassword: String = _mConfirmPassword
    private val mMobilePhone: String = _mMobilePhone
    private val mKPlusSmartCard: String = _mKPlusSmartCard
    private val mKPlusSetUpBox: String = _mKPlusSetUpBox
    //    private lateinit var mCaptchaCode: String

    fun isEmailValid(kEmail: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(kEmail).matches()
    }

    fun isPassWordValid(kPassword: String, kConfirmPassword: String): Boolean {
        return kPassword == kConfirmPassword && kPassword.length >= 8
    }
}