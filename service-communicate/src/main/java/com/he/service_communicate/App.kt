package com.he.service_communicate

import android.app.Application
import android.content.Context
import android.os.Environment

/**
 * Created by Liam.Zheng on 2021/8/4
 *
 * Des:
 */
class App : Application() {
    companion object {
        private var mApplicationContext: Context? = null
        fun getApplicationContext() = mApplicationContext
    }

    override fun onCreate() {
        super.onCreate()
        mApplicationContext = this.applicationContext
    }
}