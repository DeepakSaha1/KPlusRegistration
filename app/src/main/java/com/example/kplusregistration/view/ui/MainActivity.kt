package com.example.kplusregistration.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kplusregistration.R
import com.example.kplusregistration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  mRegisterFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val iBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mRegisterFragment = RegisterFragment()
        val iFragmentManager = supportFragmentManager
        val iFragmentTransaction = iFragmentManager.beginTransaction()

        iFragmentTransaction.add(R.id.id_frame_layout_main_activity, mRegisterFragment)
        iFragmentTransaction.addToBackStack(null)
        iFragmentTransaction.commit()
    }
}
