package com.he.module_one

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.he.arouter_api.ARouter
import com.he.lib_annotation.Route

@Route(path = "module_one/OneActivity")
class OneActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "OneActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)
    }

    fun intentToOne(view: View?) {
        Log.d(TAG, "intentToOne: ")
        ARouter.instance.jumpActivity("module_two/TwoActivity")
    }
}