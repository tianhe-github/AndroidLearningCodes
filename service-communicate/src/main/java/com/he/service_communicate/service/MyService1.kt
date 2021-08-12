package com.he.service_communicate.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.he.service_communicate.listener.IService
import com.he.service_communicate.broadcast.MyBroadcast1.Companion.IntentAction
import com.he.service_communicate.broadcast.MyBroadcast1.Companion.KEY_EXTRA
import kotlin.concurrent.thread

class MyService1 : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return MyBinder()
    }


    private fun startThread() {
        thread(start = true) {
            for (index in 1..100) {
                sendBroadcast(index)
                Thread.sleep(200)
            }
        }
    }

    private fun sendBroadcast(ext: Int) {
        val intent = Intent(IntentAction)
        intent.putExtra(KEY_EXTRA, ext)
        sendBroadcast(intent)
    }

    inner class MyBinder : Binder(), IService {
        override fun start() {
            startThread()
        }
    }
}
