package com.he.arouter_api

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import dalvik.system.DexFile
import java.util.*

/**
 * Created by Liam.Zheng on 2021/8/5
 *
 * Des:
 */
class ARouter {
    companion object {
        private const val TAG = "ARouter"
        private lateinit var activityMap: MutableMap<String, Class<out Activity?>>

        @JvmStatic
        val instance: ARouter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            activityMap = mutableMapOf()
            ARouter()
        }
    }

    private lateinit var mContext: Context

    fun init(context: Context) {
        Log.d(TAG, "init: ")
        mContext = context
        val className: List<String> = getAllActivityUtils("com.he.arouter_api")
        for (cls in className) {
            Log.d(TAG, "init: className =$cls")
            try {
                val aClass = Class.forName(cls)
                if (IRouter::class.java.isAssignableFrom(aClass)) {
                    val iRouter = aClass.newInstance() as IRouter
                    iRouter.putActivity()
                }
            } catch (e: Exception) {
                Log.d(TAG, "init: Exception =" + e.message)
            }
        }
    }

    /**
     * 将activity压入 RouteProcessor调用
     *
     * @param activityName
     * @param cls
     */
    fun putActivity(activityName: String, cls: Class<out Activity?>?) {
        Log.d(TAG, "putActivity: $activityName")
        if (cls != null && !TextUtils.isEmpty(activityName)) {
            activityMap[activityName] = cls
        }
    }

    /**
     * 通过之前定义的path就行启动
     *
     * @param activityName
     */
    fun jumpActivity(activityName: String?) {
        jumpActivity(activityName, null)
    }

    fun jumpActivity(activityName: String?, bundle: Bundle?) {
        val intent = Intent()
        val aCls: Class<out Activity?>? = activityMap[activityName]
        Log.d(TAG, "jumpActivity: $aCls")
        if (aCls == null) {
            return
        }
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        intent.setClass(mContext, aCls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        Log.d(TAG, "jumpActivity: startActivity")
        mContext.startActivity(intent)
    }

    fun getAllActivityUtils(packageName: String?): List<String> {
        val list: MutableList<String> = arrayListOf()
        val path: String
        try {
            path = mContext.packageManager.getApplicationInfo(mContext.packageName, 0).sourceDir
            val dexFile = DexFile(path)
            val enumeration: Enumeration<*> = dexFile.entries()
            while (enumeration.hasMoreElements()) {
                val name = enumeration.nextElement() as String
                if (name.contains(packageName!!)) {
                    list.add(name)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return list
    }

}