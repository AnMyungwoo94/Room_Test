package com.test.api

import android.app.Application
import android.content.Context

class App : Application() {

    //시작 - context 받아오려고 만듬. 꼭 이것처럼 안해도 되긴함
    init {
        instance = this
    }

    companion object{
        private var instance: App? = null
        fun context() : Context {
            return instance!!.applicationContext
        }
    }
    // 끝

    override fun onCreate() {
        super.onCreate()
    }


}