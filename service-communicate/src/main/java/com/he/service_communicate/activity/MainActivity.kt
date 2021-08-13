package com.he.service_communicate.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.he.service_communicate.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun service2Broadcast(view: View) {
        val intent = Intent(this, ServiceToBroadcastActivity::class.java)
        startActivity(intent)
    }

    fun service2Messager(view: View) {
        val intent = Intent(this, ServiceToMessengerActivity::class.java)
        startActivity(intent)

    }
}