package com.hanifkf12.myrecyclerview

import android.content.Context
import android.content.SharedPreferences
import java.lang.StringBuilder

class PreferenceHelper(private val context: Context) {
    companion object{
        private const val NAME = "NAME"
        private const val LOGIN = "LOGIN"
    }
    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("newPref", Context.MODE_PRIVATE)

    var name : String?
        get() {
            return sharedPreferences.getString(NAME,"null")
        }
        set(value) {
            sharedPreferences.edit().apply {
                putString(NAME,value)
                apply()
            }
        }

    var isLogin : Boolean
        get() {
            return sharedPreferences.getBoolean(LOGIN,false)
        }
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(LOGIN,value)
                apply()
            }
        }
}