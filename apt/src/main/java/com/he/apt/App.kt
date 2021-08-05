package com.he.apt

import android.app.Application
import android.util.Log
import com.he.arouter_api.ARouter
import dalvik.system.DexFile
import java.util.*

/**
 * Created by Liam.Zheng on 2021/8/4
 *
 * Des:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.instance.init(this)
    }
}