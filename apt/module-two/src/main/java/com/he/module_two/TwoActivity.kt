package com.he.module_two

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.he.arouter_api.ARouter
import com.he.lib_annotation.Route

@Route(path = "module_two/TwoActivity")
class TwoActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "TwoActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
    }

    fun intentToOne(view: View?) {
        Log.d(TAG, "intentToOne: ")
        ARouter.instance.jumpActivity("main/MainActivity")
    }
}