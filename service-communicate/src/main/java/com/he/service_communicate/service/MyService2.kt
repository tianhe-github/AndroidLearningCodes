package com.he.service_communicate.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import kotlin.concurrent.thread
import android.os.Bundle
import android.util.Log


class MyService2 : Service() {
    private val mMessenger = Messenger(MessengerHandler())

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_FROM_CLIENT -> {
                    Log.d(TAG, "handleMessage:  ${msg.data.getString(KEY_CLIENT_EXTRA)}")
                    val replyMessenger = msg.replyTo
                    thread(start = true) {
                        for (index in 1..100) {
                            val serviceMsg = Message.obtain(null, MSG_FROM_SERVICE)
                            serviceMsg.data = Bundle().apply {
                                putInt(KEY_SERVICE_EXTRA, index)
                            }
                            try {
                                replyMessenger?.send(serviceMsg)
                            } catch (e: Exception) {
                            }
                            Thread.sleep(200)
                        }
                    }
                }
                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mMessenger.binder
    }


    companion object {
        private const val TAG = "MyService2"
        const val MSG_FROM_CLIENT = 0
        const val MSG_FROM_SERVICE = 1
        const val KEY_SERVICE_EXTRA = "key_service_extra"
        const val KEY_CLIENT_EXTRA = "key_client_extra"
    }


}
