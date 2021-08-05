package com.he.apt

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.he.arouter_api.ARouter
import com.he.lib_annotation.Route

@Route(path = "main/MainActivity")
class MainActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun intentToOne(view: View?) {
        Log.d(TAG, "intentToOne: ")
        ARouter.instance.jumpActivity("module_one/OneActivity")
    }
}