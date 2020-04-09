package com.hanifkf12.myrecyclerview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit

class Main2Activity : AppCompatActivity() {
    var TAG = Main2Activity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val sharedPref = getSharedPreferences("coba", Context.MODE_PRIVATE)

        //Set Data SharedPref
        val editor = sharedPref.edit()
        editor.putBoolean("status", false)
        editor.putString("nama","SIapa")
        editor.apply()

        sharedPref.edit().apply {
            putString("name","Hanif asdasd asdasd")
            putBoolean("isLogin", true)
            apply()
        }

        //Get Data SharedPref
        Log.d(TAG, sharedPref.getString("name","kosong mas")!!)
        Toast.makeText(this,sharedPref.getString("name","Tidak ADA")!!, Toast.LENGTH_SHORT).show()

        val preferenceHelper = PreferenceHelper(this)
        preferenceHelper.status = true //set
        preferenceHelper.status //get
        preferenceHelper.name = "HANIIIIFFFF"
        preferenceHelper.name
        preferenceHelper.isLogin = true
        Log.d(TAG, preferenceHelper.name!!)


    }
}
